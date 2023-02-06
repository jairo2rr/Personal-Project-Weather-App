package com.example.customweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customweatherapp.main.getFilterDuringDay
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay

class WeatherDayViewModel():ViewModel() {
    private val _listWeather = MutableLiveData<List<WeatherPerDay>>(emptyList())
    val listWeather:LiveData<List<WeatherPerDay>> get() = _listWeather

    fun updateListWeather(data:PrincipalData?,time:String?){
        _listWeather.value =  data?.getFilterDuringDay(time)
    }
}
