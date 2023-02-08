package com.example.customweatherapp.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.customweatherapp.databinding.ActivityAddPlanBinding
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.WeatherPerDay
import com.example.customweatherapp.data.model.plan.PlanWeather
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.recycler.WeatherNextDaysAdapter
import com.example.customweatherapp.ui.viewmodel.PlanViewModel

class AddPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlanBinding
    private var dataStoraged: PrincipalData? = prefers.getData()
    private val planViewModel: PlanViewModel by viewModels()
    private var itemSelected: WeatherPerDay? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tpNotification.setIs24HourView(true)
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
                binding.tpNotification.hour,
                binding.tpNotification.minute,
                "",
                itemSelected!!.dt_txt.toDate(),
                itemSelected!!.weather[0].icon,
                itemSelected!!.main.temp.toString()
            )
            planViewModel.savePlan(plan)
            planViewModel.updateListPlans()
            finish()
            return
        }
        Toast.makeText(this,"Debe rellenar todos los datos!",Toast.LENGTH_SHORT).show()

    }

    private fun updateDateSelected(item: WeatherPerDay) {
        binding.tvDateSelected.text = item.getDateComplete()
        itemSelected = item
    }
}