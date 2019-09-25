package com.example.weatherupdates.api;

import com.example.weatherupdates.model.WeatherUpdateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherUpdatesApiService {
    @GET("weather")
    Call<WeatherUpdateResponse> getWeatherUpdates(@Query("lat") String lat,
                                                  @Query("lon") String lon,
                                                  @Query("appid") String appid);
}
