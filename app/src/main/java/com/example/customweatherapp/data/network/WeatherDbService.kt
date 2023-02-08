package com.example.customweatherapp.data.network

import com.example.customweatherapp.core.RetrofitHelper
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.explorar.CityLocalized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class WeatherDbService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPrincipalData(latitud: Double, longitud: Double, apikey: String): PrincipalData? {

        return withContext(Dispatchers.IO) {
            val response = retrofit.create(WeatherDbClient::class.java)
                .getPrincipalData(latitud, longitud, apikey)
            response.body()
        }
    }

    suspend fun getCities(city:String, apiKey: String):CityLocalized{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(WeatherDbClient::class.java)
                .getCities(city = city, apikey = apiKey)
            response.body() ?: CityLocalized()
        }
    }
}