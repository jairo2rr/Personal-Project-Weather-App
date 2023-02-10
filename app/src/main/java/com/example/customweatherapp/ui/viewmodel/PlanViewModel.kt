package com.example.customweatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customweatherapp.data.model.plan.ListPlans
import com.example.customweatherapp.data.model.plan.PlanWeather
import com.example.customweatherapp.CustomWeatherApplication.Companion.prefers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(): ViewModel() {
    private val _listPlans = MutableLiveData<ListPlans?>(prefers.getListPlans())
    val listPlans: LiveData<ListPlans?> get() =_listPlans

    private val _itemDeleted = MutableLiveData<PlanWeather>()
    val itemDeleted:LiveData<PlanWeather> get() = _itemDeleted
    private val _positionDeleted = MutableLiveData<Int>(-1)
    val positionDeleted:LiveData<Int> get() = _positionDeleted

    private val _extraList = MutableLiveData<ListPlans?>(listPlans.value)
    val extraList: LiveData<ListPlans?> get() =_extraList

    private var tempList = extraList.value

    fun updateListPlans() {
        _listPlans.value = prefers.getListPlans()
        _extraList.value = _listPlans.value
        tempList = extraList.value
    }

    fun setItemDeleted(plan: PlanWeather, position: Int){
        _positionDeleted.value = position
        _itemDeleted.value = plan
        tempList = extraList.value
    }

    fun savePlan(plan: PlanWeather) {
        if (listPlans.value == null) {
            val newList = ListPlans()
            newList.add(plan)
            prefers.saveListPlans(newList)
        } else {
            val extraList = listPlans.value!!
            extraList.add(plan)
            prefers.saveListPlans(extraList)
        }
    }
    
    fun restoredPositionDeleted(){_positionDeleted.value = -1}
}