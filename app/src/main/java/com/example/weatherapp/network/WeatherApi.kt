package com.example.weatherapp.network

import com.example.weatherapp.model.MainWeatherModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

const val WEATHER_KEY = "c6e381d8c7ff98f0fee43775817cf6ad"

interface WeatherApi {

    @GET("data/2.5/weather")
    fun getWeatherData(
        @Query("units") units: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appId") appId: String
    ): Call<MainWeatherModel>
}