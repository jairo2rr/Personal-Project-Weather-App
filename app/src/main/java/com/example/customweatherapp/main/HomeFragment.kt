package com.example.customweatherapp.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentHomeBinding
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter
import com.example.customweatherapp.viewmodel.HomeViewModel
import com.example.customweatherapp.viewmodel.HomeViewModelFactory

private const val LATITUDE = "LATITUDE"
private const val LONGITUDE = "LONGITUDE"

class HomeFragment : Fragment() {
    private var latitude: Double = arguments?.getDouble(LATITUDE) ?: 0.0
    private var longitude: Double = arguments?.getDouble(LONGITUDE) ?: 0.0

    private var timeForDetail = ""

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory(
            latitude,
            longitude,
            getString(R.string.api_key)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherNextDayAdapter = WeatherNextDaysAdapter(emptyList(), "perday") { item ->
            run {
                changePrincipalCard(item)
                timeForDetail = item.dt_txt
            }
        }

        binding.recyclerNextDays.adapter = weatherNextDayAdapter
        homeViewModel.principalData.value!!
        binding.btnVerMas.setOnClickListener {
            initWeatherDayActivity(timeForDetail)
        }
        homeViewModel.principalData.observe(viewLifecycleOwner) { data ->
            updateInfoCity(data)
        }
        homeViewModel.listWeather.observe(viewLifecycleOwner) {

            weatherNextDayAdapter.listWeather = it
            weatherNextDayAdapter.notifyDataSetChanged()
            if (it.isNotEmpty()) {
                changePrincipalCard(it[0])
                timeForDetail = it[0].dt_txt
            }
        }

    }

    private fun updateInfoCity(data: PrincipalData) {
        val city = data.city
        binding.tvCityRegion.text = city.name
        binding.tvCountry.text = city.country
    }

    private fun changePrincipalCard(firstData: WeatherPerDay) {
        binding.imgIcon.load("https://openweathermap.org/img/wn/${firstData.weather[0].icon}@2x.png")
        binding.tvClima.text = firstData.weather[0].main
        binding.tvEstado.text = firstData.weather[0].description
        val date = firstData.getDateComplete()
        binding.tvDay.text = date
        binding.tvTemperature.text = "${firstData.main.temp}°"
        binding.tvVarianza.text = "${firstData.main.temp_min}° - ${firstData.main.temp_max}"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initWeatherDayActivity(time: String) {
        val intent = Intent(binding.root.context, WeatherDayActivity::class.java).apply {
            putExtra("data", homeViewModel.principalData.value)
            putExtra("dt_txt", time)
        }
        startActivity(intent)
    }

}