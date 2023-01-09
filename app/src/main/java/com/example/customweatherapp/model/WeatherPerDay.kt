package com.example.customweatherapp.model

import java.io.Serializable

data class WeatherPerDay(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
):Serializable