package com.example.customweatherapp.data

import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.explorar.CityLocalized
import com.example.customweatherapp.data.network.WeatherDbService
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val api:WeatherDbService) {

    suspend fun getPrincipalData(lat:Double,lon:Double,apiKey:String):PrincipalData?{
        val response = api.getPrincipalData(lat,lon,apiKey)
        return response
    }

    suspend fun getCities(city:String,apiKey:String):CityLocalized{
        val response = api.getCities(city = city, apiKey = apiKey)
        return response
    }
}