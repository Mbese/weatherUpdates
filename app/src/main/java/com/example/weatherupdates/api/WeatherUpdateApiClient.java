package com.example.weatherupdates.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherUpdateApiClient {

    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    public Retrofit.Builder getRetrofitInstance() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);
    }

    public WeatherUpdatesApiService getService() {
        return getRetrofitInstance().build().create(WeatherUpdatesApiService.class);
    }
}
