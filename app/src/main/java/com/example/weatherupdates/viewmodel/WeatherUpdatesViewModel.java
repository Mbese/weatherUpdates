package com.example.weatherupdates.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherupdates.api.WeatherUpdateApiClient;
import com.example.weatherupdates.model.WeatherUpdateResponse;
import com.example.weatherupdates.repo.IWeatherUpdatesRepo;
import com.example.weatherupdates.repo.WeatherForecastCache;
import com.example.weatherupdates.repo.WeatherUpdatesRepo;

public class WeatherUpdatesViewModel extends ViewModel {
    public  MutableLiveData<WeatherUpdateResponse> liveData;
    private IWeatherUpdatesRepo repo;

    public WeatherUpdatesViewModel(){
        this(new MutableLiveData<WeatherUpdateResponse>(), new WeatherUpdatesRepo(new WeatherUpdateApiClient().getService(), WeatherForecastCache.getInstance()));
    }

    public WeatherUpdatesViewModel(MutableLiveData<WeatherUpdateResponse> liveData, IWeatherUpdatesRepo repo) {
        this.liveData = liveData;
        this.repo = repo;
    }

    public void loadWeatherUpdates(String lat, String lon) {
        repo.getWeatherUpdates(liveData, lat, lon);
    }
}
