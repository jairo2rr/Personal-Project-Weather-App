package com.example.customweatherapp.data.network

import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.explorar.CityLocalized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherDbService @Inject constructor(private val api:WeatherDbClient){

    suspend fun getPrincipalData(latitud: Double, longitud: Double, apikey: String): PrincipalData? {

        return withContext(Dispatchers.IO) {
            val response = api.getPrincipalData(latitud, longitud, apikey)
            response.body()
        }
    }

    suspend fun getCities(city:String, apiKey: String):CityLocalized{
        return withContext(Dispatchers.IO){
            val response = api.getCities(city = city, apikey = apiKey)
            response.body() ?: CityLocalized()
        }
    }
}