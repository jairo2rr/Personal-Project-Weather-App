package com.example.customweatherapp.core

import com.example.customweatherapp.data.network.WeatherDbClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofit():Retrofit{
        return retrofit
    }

//    val service = retrofit.create(WeatherDbClient::class.java)
}