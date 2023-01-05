package com.example.customweatherapp.model.service

import com.example.customweatherapp.model.PrincipalData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDbService {
    @GET("forecast?units=metric")
    suspend fun getPrincipalData(@Query("lat") latitud:Double,@Query("lon") longitud:Double,@Query("appid") apikey:String):PrincipalData
}