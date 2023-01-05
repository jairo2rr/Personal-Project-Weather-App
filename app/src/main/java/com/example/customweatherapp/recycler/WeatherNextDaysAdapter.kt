package com.example.customweatherapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customweatherapp.databinding.ItemNextDaysBinding
import com.example.customweatherapp.model.WeatherPerDay

class WeatherNextDaysAdapter(var listWeather: List<WeatherPerDay>):RecyclerView.Adapter<NextWeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextWeatherHolder {
        val binding = ItemNextDaysBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NextWeatherHolder(binding)
    }

    override fun onBindViewHolder(holder: NextWeatherHolder, position: Int) {
        holder.bind(listWeather[position])
    }

    override fun getItemCount(): Int = listWeather.size
}