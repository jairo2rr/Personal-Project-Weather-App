package com.example.customweatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.data.database.entities.CityItemEntity
import com.example.customweatherapp.data.model.explorar.CityItemModel
import com.example.customweatherapp.domain.GetCitiesUC
import com.example.customweatherapp.domain.model.CityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val repository: WeatherDbRepository,
    private val getCitiesUC: GetCitiesUC
) : ViewModel() {
    private val _listCities = MutableLiveData<List<CityItem>>(emptyList())
    val listCities: LiveData<List<CityItem>> get() = _listCities

    init {
       getListFromDb()
    }

    fun searchCity(nameCity: String, apiKey: String) {
        viewModelScope.launch {
            val citiesLocalized = getCitiesUC(nameCity, apiKey)
            _listCities.value = citiesLocalized
        }
    }

    fun addToListSearched(item: CityItem) {
        viewModelScope.launch {
            repository.insertInto(item)
        }
        // Method to modify a list to a mutable list
        /*_listToAdd.value = _listToAdd.value?.toMutableList()?.apply {
            add(item)
        }*/
    }

    fun getListFromDb(){
        viewModelScope.launch {
            _listCities.value = repository.getAllCitiesFromDB()
        }
    }

}
