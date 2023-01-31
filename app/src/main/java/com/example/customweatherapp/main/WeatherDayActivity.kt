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
        val weatherDuringDay = WeatherNextDaysAdapter(emptyList(),"during"){
            item -> setPrincipalCard(item)
        }
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
            setPrincipalCard(principalDataCard)
            binding.rvCardsHour.adapter = weatherDuringDay
            binding.tvCityName.text = data?.city?.name
            setExtraInformation()
        }else{
            Toast.makeText(this@WeatherDayActivity,"Error inesperado",Toast.LENGTH_LONG).show()
        }
    }

    private fun setExtraInformation() {
        binding.tvLevelSea.text = "${principalDataCard.main.sea_level}"
        binding.tvGroundprs.text = "${principalDataCard.main.grnd_level}"
        binding.tvAtmosfericprs.text = "${principalDataCard.main.pressure}"
        binding.tvHumidity.text = "${principalDataCard.main.humidity}%"
        binding.tvVisibility.text = "${principalDataCard.visibility}"
        binding.tvProbop.text = "${(principalDataCard.pop*100).toInt()}%"
    }

    private fun setPrincipalCard(itemCard: WeatherPerDay) {
        binding.tvDayText.text = itemCard.getDateComplete()
        binding.tvClima.text = "${itemCard.weather[0].main}\n${this.principalDataCard.weather[0].description}"
        binding.tvHour.text = itemCard.getHourDate()
        binding.tvTemperature.text = "${itemCard.main.temp}°"
        binding.tvFeelsLike.text = "${itemCard.main.temp_min}° - ${this.principalDataCard.main.temp_max}°"
        binding.imgWeather.load("$ICON_IMAGE${itemCard.weather[0].icon}@2x.png")
    }

    private fun getFilterDuringDay(data:PrincipalData, time:String?):List<WeatherPerDay>{
        if(time == null) return emptyList()
        return data.list.filter {
                weather -> getDate(weather.dt_txt) == getDate(time)
        }
    }

}