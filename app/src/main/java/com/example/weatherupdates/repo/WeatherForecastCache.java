package com.example.weatherupdates.repo;

import com.example.weatherupdates.model.WeatherUpdateResponse;

public class WeatherForecastCache implements IWeatherForecastCache {

    private static WeatherForecastCache forecastCache;
    private WeatherUpdateResponse weatherUpdateResponse;

    public static synchronized WeatherForecastCache getInstance() {
        if (forecastCache == null) {
            forecastCache = new WeatherForecastCache();
        }
        return forecastCache;
    }

    @Override
    public synchronized void setWeatherUpdatesResponse(WeatherUpdateResponse weatherUpdatesResponse) {
        if (weatherUpdateResponse != null) {
            this.weatherUpdateResponse = weatherUpdatesResponse;
        }
    }

    @Override
    public synchronized WeatherUpdateResponse getWeatherUpdatesResponse() {
        return weatherUpdateResponse;
    }

    public void flush() {
        weatherUpdateResponse = null;
    }
}
