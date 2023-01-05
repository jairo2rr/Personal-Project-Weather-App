package com.example.customweatherapp.recycler

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.customweatherapp.databinding.ActivityMainBinding
import com.example.customweatherapp.databinding.ItemNextDaysBinding
import com.example.customweatherapp.model.Weather
import com.example.customweatherapp.model.WeatherPerDay

class NextWeatherHolder(val binding: ItemNextDaysBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(weatherPerDay: WeatherPerDay){
        binding.tvDayText.text = weatherPerDay.dt_txt
        binding.tvTemperature.text = "${weatherPerDay.main.temp}°"
        binding.imgIconWeather.load("https://openweathermap.org/img/wn/${weatherPerDay.weather[0].icon}@2x.png")
    }
}