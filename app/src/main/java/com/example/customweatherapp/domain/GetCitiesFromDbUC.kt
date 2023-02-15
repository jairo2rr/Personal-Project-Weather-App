package com.example.customweatherapp.domain

import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.domain.model.CityItem

class GetCitiesFromDbUC(private val repository: WeatherDbRepository){
    suspend operator fun invoke():List<CityItem>{
        return repository.getAllCitiesFromDB()
    }
}