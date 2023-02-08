package com.example.customweatherapp.data.model.explorar

data class CityLocalizedItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)