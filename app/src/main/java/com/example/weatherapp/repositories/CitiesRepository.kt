package com.example.weatherapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.city.CityDataModel
import com.example.weatherapp.network.CityApi
import com.example.weatherapp.network.NetworkConstants
import com.example.weatherapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val BASE_CITY_URL = "https://restcountries.eu/"
class CitiesRepository(private val retrofit: RetrofitClient) {
    private lateinit var api: CityApi

    fun getCityData(capital: String): MutableLiveData<MutableList<CityDataModel>> {
        api = retrofit.retrofit(NetworkConstants.BASE_CITY_URL).create(CityApi::class.java)
        val data = MutableLiveData<MutableList<CityDataModel>>()
        api.getCityData(capital)
            .enqueue(object : Callback<MutableList<CityDataModel>> {
                override fun onResponse(
                    call: Call<MutableList<CityDataModel>>,
                    response: Response<MutableList<CityDataModel>>
                ) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<MutableList<CityDataModel>>, t: Throwable) {
                    data.value = null
                }
            })
        return data
    }

}