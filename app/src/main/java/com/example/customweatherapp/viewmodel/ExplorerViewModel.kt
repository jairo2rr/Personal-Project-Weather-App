package com.example.customweatherapp.viewmodel

import androidx.lifecycle.*
import com.example.customweatherapp.model.explorar.CityLocalized
import com.example.customweatherapp.model.service.WeatherDbClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExplorerViewModel(private val apiKey:String):ViewModel() {
    private val _listCities = MutableLiveData<CityLocalized>(CityLocalized())
    val listCities:LiveData<CityLocalized> get() = _listCities

    fun searchCity(nameCity:String){
        viewModelScope.launch {
            val citiesLocalized = WeatherDbClient.service.getCities(city = nameCity, apikey = apiKey)
            _listCities.value = citiesLocalized ?: CityLocalized()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ExplorerViewModelFactory(private val apiKey:String):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExplorerViewModel(apiKey) as T
    }
}