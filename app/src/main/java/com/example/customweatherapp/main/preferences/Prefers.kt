package com.example.customweatherapp.main.preferences

import android.content.Context
import com.example.customweatherapp.model.PrincipalData
import com.google.gson.Gson

class Prefers(context:Context) {

    val SHARED_NAME = "DBDataWeather"
    val STORAGED_DATA = "principalData"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveData(data:PrincipalData){
        val json = Gson().toJson(data)
        storage.edit().putString(STORAGED_DATA,json).apply()
    }

    fun getData():PrincipalData?{
        val json = storage.getString(STORAGED_DATA,null) ?: return null
        return Gson().fromJson(json,PrincipalData::class.java)
    }
}