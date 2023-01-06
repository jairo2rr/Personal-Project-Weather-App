package com.example.customweatherapp.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentHomeBinding
import com.example.customweatherapp.model.City
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.model.service.WeatherDbClient
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

private const val LATITUDE = "LATITUDE"
private const val LONGITUDE = "LONGITUDE"

class HomeFragment : Fragment() {
    private var latitude:Double? = null
    private var longitude: Double? = null

    private lateinit var binding :FragmentHomeBinding
    private val listWeather = emptyList<WeatherPerDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble(LATITUDE)
            longitude = it.getDouble(LONGITUDE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherNextDayAdapter = WeatherNextDaysAdapter(listWeather)

        binding.recyclerNextDays.adapter = weatherNextDayAdapter
        lifecycleScope.launch {
            val apikey = getString(R.string.api_key)
            val data = WeatherDbClient.service.getPrincipalData(latitude!!, longitude!!, apikey)
            val firstData = data.list[0]
            weatherNextDayAdapter.listWeather = data.list
            weatherNextDayAdapter.notifyDataSetChanged()
            updateInfoCity(data.city)
            changePrincipalCard(firstData)
        }
    }

    private fun updateInfoCity(city: City) {
        binding.tvCityRegion.text = city.name
        binding.tvCountry.text = city.country
    }

    private fun changePrincipalCard(data:WeatherPerDay) {
        binding.imgIcon.load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
        binding.tvClima.text = data.weather[0].main
        binding.tvEstado.text = data.weather[0].description
        binding.tvDay.text = data.dt_txt
        binding.tvTemperature.text = "${data.main.temp}°"
        binding.tvVarianza.text = "${data.main.temp_min}° - ${data.main.temp_max}"
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(LATITUDE, param1)
                    putString(LONGITUDE, param2)
                }
            }
    }
}