package com.example.customweatherapp

import android.app.Application
import com.example.customweatherapp.preferences.Prefers
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomWeatherApplication:Application() {

    companion object{
        lateinit var prefers: Prefers
    }

    override fun onCreate() {
        super.onCreate()
        prefers = Prefers(applicationContext)
    }
}