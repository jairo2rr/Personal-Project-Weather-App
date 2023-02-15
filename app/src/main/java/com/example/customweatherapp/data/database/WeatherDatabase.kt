package com.example.customweatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.customweatherapp.data.database.dao.CityItemDao
import com.example.customweatherapp.data.database.entities.CityItemEntity

@Database(entities = [CityItemEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getCityItemDao(): CityItemDao
}