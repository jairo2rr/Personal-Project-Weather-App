package com.example.customweatherapp.preferences

import android.content.Context
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.plan.ListPlans
import com.google.gson.Gson

class Prefers(context:Context) {

    val SHARED_NAME = "DBDataWeather"
    val STORAGED_DATA = "principalData"
    val STORAGED_LIST_PLAN = "listPlans"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveData(data:PrincipalData){
        val json = Gson().toJson(data)
        storage.edit().putString(STORAGED_DATA,json).apply()
    }

    fun getData():PrincipalData?{
        val json = storage.getString(STORAGED_DATA,null) ?: return null
        return Gson().fromJson(json,PrincipalData::class.java)
    }

    fun saveListPlans(listPlan:ListPlans){
        val json = Gson().toJson(listPlan)
        storage.edit().putString(STORAGED_LIST_PLAN,json).apply()
    }

    fun getListPlans():ListPlans?{
        val json = storage.getString(STORAGED_LIST_PLAN,null) ?: return  null
        return Gson().fromJson(json,ListPlans::class.java)
    }
}