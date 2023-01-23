package com.example.customweatherapp.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.example.customweatherapp.databinding.ActivityWeatherDayBinding
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter


class WeatherDayActivity : AppCompatActivity() {
    companion object{
        private const val EXTRA_DATA = "data"
        private const val EXTRA_TIME = "dt_txt"
        private const val ICON_IMAGE = "https://openweathermap.org/img/wn/"
    }
    private lateinit var binding:ActivityWeatherDayBinding
    private var data:PrincipalData? = null
    private var time:String? = null

    private lateinit var dataFiltered:List<WeatherPerDay>

    private lateinit var principalDataCard: WeatherPerDay

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWeatherDayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val weatherDuringDay = WeatherNextDaysAdapter(emptyList(),"during")
        time = intent.getStringExtra(EXTRA_TIME)

        // Verificar si este codigo estas bien
        if (Build.VERSION.SDK_INT < 33) {
            data = intent.extras?.getSerializable(EXTRA_DATA) as PrincipalData
        }else{
            data = intent.extras?.getSerializable(EXTRA_DATA,PrincipalData::class.java)
        }

        if(data != null){
            principalDataCard = data!!.list.first { perDay -> perDay.dt_txt == time }
            dataFiltered =  getFilterDuringDay(data!!,time)
            weatherDuringDay.listWeather = dataFiltered
            weatherDuringDay.notifyDataSetChanged()
        }
        setPrincipalCard(data)
        binding.rvCardsHour.adapter = weatherDuringDay
        binding.tvCityName.text = data?.city?.name
        setExtraInformation(data)
    }

    private fun setExtraInformation(data: PrincipalData?) {
        if(data != null){
            binding.tvLevelSea.text = "${principalDataCard.main.sea_level}"
            binding.tvGroundprs.text = "${principalDataCard.main.grnd_level}"
            binding.tvAtmosfericprs.text = "${principalDataCard.main.pressure}"
            binding.tvHumidity.text = "${principalDataCard.main.humidity}%"
            binding.tvVisibility.text = "${principalDataCard.visibility}"
            binding.tvProbop.text = "${(principalDataCard.pop*100).toInt()}%"
            return
        }
        Toast.makeText(this@WeatherDayActivity,"Error inesperado",Toast.LENGTH_LONG).show()
    }

    private fun setPrincipalCard(data: PrincipalData?) {
        if(data != null){
            binding.tvDayText.text = principalDataCard.getDateComplete()
            binding.tvClima.text = "${principalDataCard.weather[0].main}\n${principalDataCard.weather[0].description}"
            binding.tvHour.text = principalDataCard.getHourDate()
            binding.tvTemperature.text = "${principalDataCard.main.temp}°"
            binding.tvFeelsLike.text = "${principalDataCard.main.temp_min}° - ${principalDataCard.main.temp_max}°"
            binding.imgWeather.load("$ICON_IMAGE${principalDataCard.weather[0].icon}@2x.png")
            return
        }
        Toast.makeText(this@WeatherDayActivity,"Error inesperado",Toast.LENGTH_LONG).show()
    }

    private fun getFilterDuringDay(data:PrincipalData, time:String?):List<WeatherPerDay>{
        if(time == null) return emptyList()
        return data.list.filter {
                weather -> getDate(weather.dt_txt) == getDate(time)
        }
    }

}