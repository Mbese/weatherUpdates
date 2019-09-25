package com.example.weatherupdates.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherupdates.api.WeatherUpdatesApiService;
import com.example.weatherupdates.model.WeatherUpdateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherUpdatesRepo implements IWeatherUpdatesRepo {

    private final WeatherUpdatesApiService service;
    private final IWeatherForecastCache cache;

    public WeatherUpdatesRepo(WeatherUpdatesApiService service, IWeatherForecastCache cache){
        this.service = service;
        this.cache = cache;
    }

    @Override
    public void getWeatherUpdates(final MutableLiveData<WeatherUpdateResponse> liveData, String lat, String lon) {
        if (cache.getWeatherUpdatesResponse() == null) {
            service.getWeatherUpdates(lat, lon, "db45b3ce426ef10310732973d2201af5").enqueue(new Callback<WeatherUpdateResponse>() {
                @Override
                public void onResponse(Call<WeatherUpdateResponse> call, Response<WeatherUpdateResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.setValue(response.body());
                        cache.setWeatherUpdatesResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<WeatherUpdateResponse> call, Throwable t) {
                    liveData.setValue(null);
                    Log.e("Repo", t.getMessage());
                }
            });
        } else {
            liveData.setValue(cache.getWeatherUpdatesResponse());
        }
    }
}
