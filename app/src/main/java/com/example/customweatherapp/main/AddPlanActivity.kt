package com.example.customweatherapp.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customweatherapp.databinding.ActivityAddPlanBinding
import com.example.customweatherapp.model.PrincipalData
import com.example.customweatherapp.model.WeatherPerDay
import com.example.customweatherapp.model.plan.ListPlans
import com.example.customweatherapp.model.plan.PlanWeather
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter

class AddPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlanBinding
    private var dataStoraged: PrincipalData? = prefers.getData()
    private var listPlanStoraged: ListPlans? = prefers.getListPlans()
    private var itemSelected: WeatherPerDay? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val filteredList =
            dataStoraged?.getFilterHourPerDay(dataStoraged?.list?.get(0)?.dt_txt) ?: emptyList()
        val weatherNextDayAdapter = WeatherNextDaysAdapter(filteredList, "perday") { item ->
            updateDateSelected(item)
        }
        binding.rvSelectDate.adapter = weatherNextDayAdapter

        binding.btnSavePlan.setOnClickListener { savePlan() }
    }

    private fun savePlan() {
        if (itemSelected != null) {
            val plan = PlanWeather(
                binding.etTitlePlan.text.toString(),
                binding.etDescriptionPlan.text.toString(),
                0,
                0,
                "",
                itemSelected!!.dt_txt.toDate(),
                itemSelected!!.weather[0].icon,
                itemSelected!!.main.temp.toString()
            )
            if (listPlanStoraged == null) {
                val listPlans = ListPlans()
                listPlans.add(plan)
                prefers.saveListPlans(listPlans)
            } else {
                listPlanStoraged!!.add(plan)
                prefers.saveListPlans(listPlanStoraged!!)
            }
            return
        }
        Toast.makeText(this,"Debe rellenar todos los datos!",Toast.LENGTH_SHORT).show()

    }

    private fun updateDateSelected(item: WeatherPerDay) {
        binding.tvDateSelected.text = item.getDateComplete()
        itemSelected = item
    }
}