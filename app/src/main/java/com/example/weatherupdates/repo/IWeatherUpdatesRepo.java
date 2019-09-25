package com.example.weatherupdates.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherupdates.model.WeatherUpdateResponse;

public interface IWeatherUpdatesRepo {
    void getWeatherUpdates(MutableLiveData<WeatherUpdateResponse> liveData, String lat, String lon);
}
