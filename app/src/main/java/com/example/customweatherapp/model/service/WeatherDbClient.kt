package com.example.customweatherapp.model.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherDbClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(WeatherDbService::class.java)
}