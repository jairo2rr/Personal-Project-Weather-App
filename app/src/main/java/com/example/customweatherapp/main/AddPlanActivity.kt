package com.example.customweatherapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.ActivityAddPlanBinding
import com.example.customweatherapp.main.preferences.CustomWeatherApplication
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter

class AddPlanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddPlanBinding
    private var dataStoraged: PrincipalData? = CustomWeatherApplication.prefers.getData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val filteredList = dataStoraged?.getFilterHourPerDay(dataStoraged?.list?.get(0)?.dt_txt) ?: emptyList()
        val weatherNextDayAdapter = WeatherNextDaysAdapter(filteredList, "perday"){
            item -> updateDateSelected(item)
        }

        binding.rvSelectDate.adapter = weatherNextDayAdapter
    }

    private fun updateDateSelected(item: WeatherPerDay) {
        binding.tvDateSelected.text = item.getDateComplete()
    }
}