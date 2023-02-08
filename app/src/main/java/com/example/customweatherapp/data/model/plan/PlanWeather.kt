package com.example.customweatherapp.data.model.plan

import java.util.Date


data class PlanWeather(
    val title: String,
    val description: String?,
    val hour: Int,
    val minutes: Int,
    val time: String,
    val date: Date,
    val icon:String,
    val temperature:String
)

class ListPlans : ArrayList<PlanWeather>()