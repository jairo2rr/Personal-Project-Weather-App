package com.example.customweatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customweatherapp.data.model.explorar.CityLocalized
import com.example.customweatherapp.domain.GetCitiesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplorerViewModel @Inject constructor(private val getCitiesUC: GetCitiesUC):ViewModel() {
    private val _listCities = MutableLiveData<CityLocalized>(CityLocalized())
    val listCities:LiveData<CityLocalized> get() = _listCities

    fun searchCity(nameCity:String, apiKey: String){
        viewModelScope.launch {
            val citiesLocalized = getCitiesUC(nameCity,apiKey)
            _listCities.value = citiesLocalized
        }
    }
}
