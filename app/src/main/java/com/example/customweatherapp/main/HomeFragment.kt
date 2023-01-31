package com.example.customweatherapp.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentHomeBinding
import com.example.customweatherapp.main.preferences.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.model.service.WeatherDbClient
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter
import kotlinx.coroutines.launch
import java.util.*

private const val LATITUDE = "LATITUDE"
private const val LONGITUDE = "LONGITUDE"

class HomeFragment : Fragment() {
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var data: PrincipalData? = null

    private lateinit var binding: FragmentHomeBinding
    private val listWeather = emptyList<WeatherPerDay>()

    private var dataStoraged: PrincipalData? = prefers.getData()

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

        val weatherNextDayAdapter = WeatherNextDaysAdapter(listWeather, "perday"){
            item -> changePrincipalCard(item)
        }

        binding.recyclerNextDays.adapter = weatherNextDayAdapter
        lifecycleScope.launch {
            val apikey = getString(R.string.api_key)
            data = WeatherDbClient.service.getPrincipalData(latitude!!, longitude!!, apikey)
            if (data == null) {
                Toast.makeText(view.context, "Necesita conexion a internet", Toast.LENGTH_SHORT)
                    .show()
            } else {
                prefers.saveData(data!!)
                dataStoraged = prefers.getData()
            }
            if(dataStoraged != null){
                data = dataStoraged
                val firstData = data!!.list[0]
                val dataFiltered = data!!.getFilterHourPerDay(firstData.dt_txt)
                weatherNextDayAdapter.listWeather = dataFiltered
                weatherNextDayAdapter.notifyDataSetChanged()
                updateInfoCity()
                changePrincipalCard(dataFiltered[0])
                binding.btnVerMas.setOnClickListener {
                    initWeatherDayActivity()
                }
            }else{
                Toast.makeText(view.context, "Necesita activar la localizacion para actualizar datos.", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }

    private fun updateInfoCity() {
        val city = data!!.city
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

    private fun initWeatherDayActivity() {
        val newData = data
        val intent = Intent(binding.root.context, WeatherDayActivity::class.java).apply {
            putExtra("data", newData)
            putExtra("dt_txt", newData!!.list[0].dt_txt)
        }
        startActivity(intent)
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