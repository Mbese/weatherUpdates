package com.example.weatherupdates.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.weatherupdates.R;
import com.example.weatherupdates.model.WeatherUpdateResponse;
import com.example.weatherupdates.viewmodel.WeatherUpdatesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

public class TodayWeatherFragment extends Fragment {

    private WeatherUpdatesViewModel viewModel;
    private final int PERMISSION_ID = 42;
    private FusedLocationProviderClient client;

    private TextView desc;
    private TextView temp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView sunrise;
    private TextView sunset;
    private TextView wind;
    private TextView pressure;
    private TextView humidity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (haveNetworkConnection()) {
            getLastLocation();
        } else {
            Snackbar snackbar = Snackbar.make(view.findViewById(R.id.content), "Please make sure you have internet connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        View detailsView = view.findViewById(R.id.detail);
        Button btn1 = detailsView.findViewById(R.id.btn_more_detail);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen();
            }
        });

        viewModel = ViewModelProviders.of(this).get(WeatherUpdatesViewModel.class);

        desc = view.findViewById(R.id.description);
        temp = view.findViewById(R.id.temp);
        minTemp = view.findViewById(R.id.temp_min);
        maxTemp = view.findViewById(R.id.temp_max);
        sunrise = view.findViewById(R.id.sunrise);
        sunset = view.findViewById(R.id.sunset);
        wind = view.findViewById(R.id.wind);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
    }

    @Override
    public void onResume() {
        super.onResume();
        observerResponse();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : netInfo) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (networkInfo.isConnected())
                    haveConnectedWifi = true;
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (networkInfo.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void observerResponse() {
        viewModel.liveData.observe(this, new Observer<WeatherUpdateResponse>() {
            @Override
            public void onChanged(WeatherUpdateResponse weatherUpdateResponse) {
                desc.setText(String.valueOf(weatherUpdateResponse.weather.get(0).description));
                temp.setText(weatherUpdateResponse.main.temp + "°C");
                minTemp.setText(weatherUpdateResponse.main.temp_min + "°C");
                maxTemp.setText(weatherUpdateResponse.main.temp_max + "°C");

                sunrise.setText(String.valueOf(weatherUpdateResponse.sys.sunrise));
                sunset.setText(String.valueOf(weatherUpdateResponse.sys.sunset));
                wind.setText(String.valueOf(weatherUpdateResponse.wind.speed));
                pressure.setText(String.valueOf(weatherUpdateResponse.main.pressure));
                humidity.setText(String.valueOf(weatherUpdateResponse.main.humidity));
            }
        });
    }

    private void showScreen() {
        startActivity(new Intent(getActivity(), WeatherDetailsActivity.class));
    }

    private Boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private Boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();

                        if (location == null) {
                            requestNewLocation();
                        } else {
                            viewModel.loadWeatherUpdates(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        }
                    }
                });
            } else {
                Toast.makeText(requireActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        client = LocationServices.getFusedLocationProviderClient(requireActivity());
        client.requestLocationUpdates(locationRequest, new LocationCallBack(), Looper.myLooper());
    }

    private class LocationCallBack extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            Log.e("New Location", location.getLatitude() + " AND " + location.getLongitude());
        }
    }
}
