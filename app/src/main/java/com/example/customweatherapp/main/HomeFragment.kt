package com.example.customweatherapp.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentHomeBinding
import com.example.customweatherapp.model.City
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.model.service.WeatherDbClient
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

        val weatherNextDayAdapter = WeatherNextDaysAdapter(listWeather,"perday")

        binding.recyclerNextDays.adapter = weatherNextDayAdapter
        lifecycleScope.launch {
            val apikey = getString(R.string.api_key)
            val data = WeatherDbClient.service.getPrincipalData(latitude!!, longitude!!, apikey)
            val firstData = data.list[0]
            val dataFiltered = getFilterHourPerDay(data,firstData.dt_txt)
            weatherNextDayAdapter.listWeather = dataFiltered
            weatherNextDayAdapter.notifyDataSetChanged()
            updateInfoCity(data.city)
            changePrincipalCard(firstData)
            binding.btnVerMas.setOnClickListener {
                initWeatherDayActivity(data)
            }
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
        val date = data.getDateComplete()
        binding.tvDay.text = date
        binding.tvTemperature.text = "${data.main.temp}°"
        binding.tvVarianza.text = "${data.main.temp_min}° - ${data.main.temp_max}"
    }

    private fun initWeatherDayActivity(data:PrincipalData){
        val intent = Intent(binding.root.context,WeatherDayActivity::class.java).apply {
            putExtra("data",data)
            putExtra("dt_txt",data.list[0].dt_txt)
        }
        startActivity(intent)
    }

    private fun getFilterHourPerDay(data:PrincipalData, time:String?):List<WeatherPerDay>{
        if(time == null) return emptyList()
        return data.list.filter {
                weather -> getHour(weather.dt_txt) == getHour(time)
        }
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