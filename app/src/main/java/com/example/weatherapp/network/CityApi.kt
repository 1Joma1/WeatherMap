package com.example.weatherapp.network

import com.example.weatherapp.model.city.CityDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CityApi {

    @GET("rest/v2/capital/{city}")
    fun getCityData(@Path("city") capital: String): Call<MutableList<CityDataModel>>

}