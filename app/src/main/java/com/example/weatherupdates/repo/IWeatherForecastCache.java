package com.example.weatherupdates.repo;

import com.example.weatherupdates.model.WeatherUpdateResponse;

public interface IWeatherForecastCache {
    void setWeatherUpdatesResponse(WeatherUpdateResponse weatherUpdatesResponse);
    WeatherUpdateResponse getWeatherUpdatesResponse();
}
