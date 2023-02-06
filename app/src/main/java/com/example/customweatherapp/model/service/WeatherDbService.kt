package com.example.customweatherapp.model.service

import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.explorar.CityLocalized
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDbService {
    @GET("data/2.5/forecast?units=metric")
    suspend fun getPrincipalData(@Query("lat") latitud:Double,@Query("lon") longitud:Double,@Query("appid") apikey:String):PrincipalData?

    @GET("geo/1.0/direct")
    suspend fun getCities(@Query("q") city:String,@Query("limit") limit:Int = 5,@Query("appid") apikey:String):CityLocalized?
}