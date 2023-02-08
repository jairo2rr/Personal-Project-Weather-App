package com.example.customweatherapp.ui.view

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.customweatherapp.databinding.ActivityWeatherDayBinding
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.WeatherPerDay
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter
import com.example.customweatherapp.ui.viewmodel.WeatherDayViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class WeatherDayActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_DATA = "data"
        private const val EXTRA_TIME = "dt_txt"
        private const val ICON_IMAGE = "https://openweathermap.org/img/wn/"
    }
    private lateinit var binding: ActivityWeatherDayBinding
    private var data: PrincipalData? = null
    private var time: String? = null
    private val weatherDayViewModel: WeatherDayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWeatherDayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        time = intent.getStringExtra(EXTRA_TIME)
        if (Build.VERSION.SDK_INT < 33) {
            data = intent.extras?.getSerializable(EXTRA_DATA) as PrincipalData
        }else {
            data = intent.extras?.getSerializable(EXTRA_DATA, PrincipalData::class.java)
        }
        weatherDayViewModel.updateListWeather(data,time)
        val weatherDuringDay = WeatherNextDaysAdapter(emptyList(), "during") { item ->
            setPrincipalCard(item)
        }
        if (data == null) {
            Toast.makeText(this@WeatherDayActivity, "Error inesperado", Toast.LENGTH_LONG).show()
            return
        }
        weatherDayViewModel.listWeather.observe(this) {
            weatherDuringDay.listWeather = it
            if (it.isNotEmpty()) {
                setPrincipalCard(it[0])
                setExtraInformation(it[0])
            }
            binding.rvCardsHour.adapter = weatherDuringDay
            binding.tvCityName.text = data?.city?.name
        }

    }

    private fun setExtraInformation(principalDataCard: WeatherPerDay) {
        binding.tvLevelSea.text = "${principalDataCard.main.sea_level}"
        binding.tvGroundprs.text = "${principalDataCard.main.grnd_level}"
        binding.tvAtmosfericprs.text = "${principalDataCard.main.pressure}"
        binding.tvHumidity.text = "${principalDataCard.main.humidity}%"
        binding.tvVisibility.text = "${principalDataCard.visibility}"
        binding.tvProbop.text = "${(principalDataCard.pop * 100).toInt()}%"
    }

    private fun setPrincipalCard(itemCard: WeatherPerDay) {
        binding.tvDayText.text = itemCard.getDateComplete()
        binding.tvClima.text = "${itemCard.weather[0].main}\n${itemCard.weather[0].description}"
        binding.tvHour.text = itemCard.getHourDate()
        binding.tvTemperature.text = "${itemCard.main.temp}°"
        binding.tvFeelsLike.text = "${itemCard.main.temp_min}° - ${itemCard.main.temp_max}°"
        binding.imgWeather.load("$ICON_IMAGE${itemCard.weather[0].icon}@2x.png")
    }

    override fun onBackPressed() {
        finish()
    }

}