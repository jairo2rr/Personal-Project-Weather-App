package com.example.customweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customweatherapp.model.plan.ListPlans
import com.example.customweatherapp.model.plan.PlanWeather
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers

class PlanViewModel : ViewModel() {
    private val mutableListPlans = MutableLiveData<ListPlans?>(prefers.getListPlans())
    val listPlans: LiveData<ListPlans?> get() =mutableListPlans

    fun updateListPlans() {
        mutableListPlans.value = prefers.getListPlans()
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
}