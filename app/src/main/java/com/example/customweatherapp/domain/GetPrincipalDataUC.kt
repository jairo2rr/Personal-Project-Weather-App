package com.example.customweatherapp.domain

import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.data.model.PrincipalData

class GetPrincipalDataUC {

    private val repository = WeatherDbRepository()

    suspend operator fun invoke(lat:Double,lon:Double,apiKey:String):PrincipalData?{
        return repository.getPrincipalData(lat,lon,apiKey)
    }

}