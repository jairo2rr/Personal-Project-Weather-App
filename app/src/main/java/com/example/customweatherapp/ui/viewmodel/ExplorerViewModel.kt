package com.example.customweatherapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.customweatherapp.data.model.explorar.CityLocalized
import com.example.customweatherapp.core.RetrofitHelper
import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.domain.GetCitiesUC
import kotlinx.coroutines.launch

class ExplorerViewModel(private val apiKey:String):ViewModel() {
    private val _listCities = MutableLiveData<CityLocalized>(CityLocalized())
    val listCities:LiveData<CityLocalized> get() = _listCities

    fun searchCity(nameCity:String){
        viewModelScope.launch {
            val citiesLocalized = GetCitiesUC().invoke(nameCity,apiKey)
            _listCities.value = citiesLocalized
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ExplorerViewModelFactory(private val apiKey:String):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExplorerViewModel(apiKey) as T
    }
}