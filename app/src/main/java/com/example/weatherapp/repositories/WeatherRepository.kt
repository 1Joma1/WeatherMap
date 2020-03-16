package com.example.weatherapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.MainWeatherModel
import com.example.weatherapp.model.Weather
import com.example.weatherapp.network.NetworkConstants
import com.example.weatherapp.network.RetrofitClient
import com.example.weatherapp.network.WEATHER_KEY
import com.example.weatherapp.network.WeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(private val retrofit: RetrofitClient) {
    private lateinit var api: WeatherApi

    fun getWeatherData(units: String, lat: Double, lon: Double): MutableLiveData<MainWeatherModel> {
        api = retrofit.retrofit(NetworkConstants.BASE_URL).create(WeatherApi::class.java)
        val data = MutableLiveData<MainWeatherModel>()
        api.getWeatherData(units, lat, lon, WEATHER_KEY)
            .enqueue(object : Callback<MainWeatherModel> {
                override fun onResponse(
                    call: Call<MainWeatherModel>,
                    response: Response<MainWeatherModel>
                ) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<MainWeatherModel>, t: Throwable) {
                    data.value = null
                }
            })
        return data
    }

}