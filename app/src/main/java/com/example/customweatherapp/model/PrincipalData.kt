package com.example.customweatherapp.model

data class PrincipalData(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherPerDay>,
    val city: City
)