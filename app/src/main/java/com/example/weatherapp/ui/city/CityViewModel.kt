package com.example.weatherapp.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.model.city.CityDataModel
import com.example.weatherapp.repositories.CitiesRepository

class CityViewModel(private val cRepository: CitiesRepository) : BaseViewModel() {

    fun getCityData(capital: String): MutableLiveData<MutableList<CityDataModel>> {
        return cRepository.getCityData(capital)
    }


}