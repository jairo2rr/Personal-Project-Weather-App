package com.example.customweatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customweatherapp.ui.view.getFilterDuringDay
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.WeatherPerDay
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDayViewModel @Inject constructor():ViewModel() {
    private val _listWeather = MutableLiveData<List<WeatherPerDay>>(emptyList())
    val listWeather:LiveData<List<WeatherPerDay>> get() = _listWeather

    fun updateListWeather(data: PrincipalData?, time:String?){
        _listWeather.value =  data?.getFilterDuringDay(time)
    }
}
