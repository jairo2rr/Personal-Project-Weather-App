package com.example.customweatherapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.customweatherapp.ui.view.getFilterHourPerDay
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.WeatherPerDay
import com.example.customweatherapp.domain.GetPrincipalDataUC
import com.example.customweatherapp.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPrincipalDataUC: GetPrincipalDataUC,
    private val state: SavedStateHandle,
    app:Application
) : ViewModel() {
    private val _principalData = MutableLiveData<PrincipalData?>(prefers.getData())
    val principalData: LiveData<PrincipalData?> get() = _principalData

    private val _listWeather = MutableLiveData<List<WeatherPerDay>>(emptyList())
    val listWeather: LiveData<List<WeatherPerDay>> get() = _listWeather

    init {
        val apiKey = app.getString(R.string.api_key)
        viewModelScope.launch {
            val data: PrincipalData? = getPrincipalDataUC.invoke(state["LATITUDE"] ?: 0.0, state["LONGITUDE"]?: 0.0,apiKey)
            Log.d("helloviewmodel","${state["LATITUDE"] ?: 1.0} ; ${state["LONGITUDE"] ?: 1.0}")
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

