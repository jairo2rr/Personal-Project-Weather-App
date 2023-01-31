package com.example.customweatherapp.main

import com.example.customweatherapp.model.WeatherPerDay
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

fun initCalendar(date:Date):Calendar{
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

fun WeatherPerDay.getDateComplete():String{
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt_txt)
    val dateSpanish  = SimpleDateFormat("d 'de' MMMM", Locale("es", "ES")).format(date)
    val calendar = initCalendar(date)
    val dayOfWeek = calendar.getDisplayName(DAY_OF_WEEK, LONG, Locale("es", "ES"))
    return "${dayOfWeek.replaceFirstChar { char -> char.uppercase() }}, $dateSpanish"
}


fun WeatherPerDay.getHourDate():String{
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt_txt)
    val calendar = initCalendar(date)
    var minute = calendar.get(MINUTE)
    return "${calendar.get(HOUR_OF_DAY)}:${if(minute<10) "0$minute" else minute}"
}

/*Obtiene el string en formato de 'dia/mes' */
fun getDate(possibleDate:String):String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date: Date = dateFormat.parse(possibleDate)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var month = calendar.get(Calendar.MONTH)

    return "$day/$month"
}

/*Obtiene el string en formato de 'hora/minutos' */
fun getHour(possibleDate:String):String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date: Date = dateFormat.parse(possibleDate)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    var minute = calendar.get(Calendar.MINUTE)

    return "$hour:$minute"
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.parse(this) as Date
}