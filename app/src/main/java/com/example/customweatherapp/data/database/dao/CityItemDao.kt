package com.example.customweatherapp.data.database.dao

import androidx.room.*
import com.example.customweatherapp.data.database.entities.CityItemEntity

@Dao
interface CityItemDao {
    @Query("SELECT * FROM city_item_table ORDER BY id DESC")
    suspend fun getAllCities():List<CityItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city:CityItemEntity)

    @Query("SELECT * FROM city_item_table WHERE country = :name_country AND name = :name")
    suspend fun searchCity(name_country:String,name:String):List<CityItemEntity>

    @Delete
    suspend fun deleteCity(city:CityItemEntity)
}