package com.example.customweatherapp.main

import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay

fun PrincipalData.getFilterHourPerDay( time: String?): List<WeatherPerDay> {
    if (time == null) return emptyList()
    return this.list.filter { weather ->
        getHour(weather.dt_txt) == getHour(time)
    }
}