package com.jaykallen.appweather.utils;

// Created by Jay Kallen on 9/14/2017

import com.jaykallen.appweather.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestAPIService {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/?";

    @GET("weather")
    Call<WeatherModel> queryWeather(@Query("q") String city, @Query("appid") String appid);

}
