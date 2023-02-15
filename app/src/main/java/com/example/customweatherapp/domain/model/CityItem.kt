package com.example.customweatherapp.domain.model

import com.example.customweatherapp.data.database.entities.CityItemEntity
import com.example.customweatherapp.data.model.explorar.CityItemModel

data class CityItem(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String?
)

fun CityItemModel.toDomain() = CityItem(country, lat, lon, name, state)
fun CityItemEntity.toDomain() = CityItem(country, lat, lon, name, state)