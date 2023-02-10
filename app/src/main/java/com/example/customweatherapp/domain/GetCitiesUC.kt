package com.example.customweatherapp.domain

import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.data.model.explorar.CityLocalized
import javax.inject.Inject

class GetCitiesUC @Inject constructor(private val repository:WeatherDbRepository){
    suspend operator fun invoke(city:String,apiKey:String):CityLocalized{
        return repository.getCities(city,apiKey)
    }
}