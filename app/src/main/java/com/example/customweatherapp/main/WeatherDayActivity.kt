package com.example.customweatherapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customweatherapp.databinding.ActivityMainBinding

class WeatherDayActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}