package com.example.customweatherapp.domain

import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.data.model.explorar.CityItemModel
import com.example.customweatherapp.domain.model.CityItem
import javax.inject.Inject

class GetCitiesUC @Inject constructor(private val repository:WeatherDbRepository){
    suspend operator fun invoke(city:String,apiKey:String):List<CityItem>{
        return repository.getCities(city,apiKey)
    }
}