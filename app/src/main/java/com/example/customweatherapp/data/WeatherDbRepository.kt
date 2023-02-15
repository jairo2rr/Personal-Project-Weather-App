package com.example.customweatherapp.data

import android.util.Log
import com.example.customweatherapp.data.database.dao.CityItemDao
import com.example.customweatherapp.data.database.entities.toEntity
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.network.WeatherDbService
import com.example.customweatherapp.domain.model.CityItem
import com.example.customweatherapp.domain.model.toDomain
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val api: WeatherDbService,
    private val cityItemDao: CityItemDao
) {

    suspend fun getPrincipalData(lat: Double, lon: Double, apiKey: String): PrincipalData? {
        val response = api.getPrincipalData(lat, lon, apiKey)
        return response
    }

    suspend fun getCities(city: String, apiKey: String): List<CityItem> {
        val response = api.getCities(city = city, apiKey = apiKey)
        response.forEach {
            Log.d("city_contain", "this: ${it.toString()}")
        }
        return response.map {
            it.toDomain()
        }
    }

    suspend fun getAllCitiesFromDB(): List<CityItem> {
        val response = cityItemDao.getAllCities()
        return response.map { it.toDomain() }
    }

    suspend fun insertInto(city: CityItem) {
        val citiesFound = cityItemDao.searchCity(city.country, city.name)
        if(citiesFound.isNotEmpty())
            cityItemDao.deleteCity(citiesFound[0])
        cityItemDao.insertCity(city.toEntity())
    }
}