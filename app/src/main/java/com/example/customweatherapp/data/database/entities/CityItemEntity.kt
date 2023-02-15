package com.example.customweatherapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.customweatherapp.domain.model.CityItem

@Entity(tableName = "city_item_table")
data class CityItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "state") val state: String?
)

fun CityItem.toEntity() = CityItemEntity(
    country = country,
    lat = lat,
    lon = lon,
    name = name,
    state = state
)