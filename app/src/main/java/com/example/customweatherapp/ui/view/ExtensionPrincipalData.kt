package com.example.customweatherapp.ui.view

import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.WeatherPerDay

fun PrincipalData.getFilterHourPerDay(time: String?): List<WeatherPerDay> {
    if (time == null) return emptyList()
    return this.list.filter { weather ->
        getHour(weather.dt_txt) == getHour(time)
    }
}

fun PrincipalData.getFilterDuringDay(time: String?): List<WeatherPerDay> {
    if (time == null) return emptyList()
    return this.list.filter { weather ->
        getDate(weather.dt_txt) == getDate(time)
    }
}