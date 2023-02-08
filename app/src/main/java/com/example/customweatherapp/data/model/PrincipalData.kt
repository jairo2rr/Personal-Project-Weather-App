package com.example.customweatherapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PrincipalData(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherPerDay>,
    val city: City
):Serializable