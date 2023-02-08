package com.example.customweatherapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.customweatherapp.ui.view.getFilterHourPerDay
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.WeatherPerDay
import com.example.customweatherapp.core.RetrofitHelper
import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.domain.GetPrincipalDataUC
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers
import kotlinx.coroutines.launch

class HomeViewModel(lat: Double, lon: Double, apiKey: String) : ViewModel() {
    private val _principalData = MutableLiveData<PrincipalData>(prefers.getData())
    val principalData: LiveData<PrincipalData> get() = _principalData

    private val _listWeather = MutableLiveData<List<WeatherPerDay>>(emptyList())
    val listWeather: LiveData<List<WeatherPerDay>> get() = _listWeather

    init {
        viewModelScope.launch {
            val data: PrincipalData? = GetPrincipalDataUC().invoke(lat, lon, apiKey)

            data?.let {
                prefers.saveData(it)
                _principalData.value = it
            }
            val firstData = data?.list?.get(0)
            _listWeather.value = data?.getFilterHourPerDay(firstData?.dt_txt)
        }
    }

    fun updateStoragedPrincipalData() {
        _principalData.value = prefers.getData()
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val lat: Double,
    private val lon: Double,
    private val apiKey: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(lat, lon, apiKey) as T
    }
}
