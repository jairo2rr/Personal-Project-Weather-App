package com.example.customweatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.customweatherapp.data.database.WeatherDatabase
import com.example.customweatherapp.data.model.Weather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "weather_db"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideCityItemDao(db:WeatherDatabase) = db.getCityItemDao()
}