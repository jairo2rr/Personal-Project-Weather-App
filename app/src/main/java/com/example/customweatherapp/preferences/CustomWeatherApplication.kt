package com.example.customweatherapp.preferences

import android.app.Application

class CustomWeatherApplication:Application() {

    companion object{
        lateinit var prefers: Prefers
    }

    override fun onCreate() {
        super.onCreate()
        prefers = Prefers(applicationContext)
    }
}