package com.example.customweatherapp.recycler

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.customweatherapp.databinding.ItemNextDaysBinding
import com.example.customweatherapp.ui.view.getDateComplete
import com.example.customweatherapp.ui.view.getHourDate
import com.example.customweatherapp.data.model.WeatherPerDay

class NextWeatherHolder(val binding: ItemNextDaysBinding,val type: String):RecyclerView.ViewHolder(binding.root){
    fun bind(weatherPerDay: WeatherPerDay){
        binding.tvDayText.text = when(type){
            "during" ->  weatherPerDay.getHourDate()
            "perday" -> weatherPerDay.getDateComplete()
            else -> ""
        }
        binding.tvTemperature.text = "${weatherPerDay.main.temp}°"
        binding.imgIconWeather.load("https://openweathermap.org/img/wn/${weatherPerDay.weather[0].icon}@2x.png")
    }
}