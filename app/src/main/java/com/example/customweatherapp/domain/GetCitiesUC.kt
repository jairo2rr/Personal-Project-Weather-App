package com.example.customweatherapp.domain

import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.data.model.explorar.CityLocalized

class GetCitiesUC {
    private val repository = WeatherDbRepository()

    suspend operator fun invoke(city:String,apiKey:String):CityLocalized{
        return repository.getCities(city,apiKey)
    }
}