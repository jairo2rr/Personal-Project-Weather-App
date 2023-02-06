package com.example.customweatherapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.customweatherapp.databinding.ItemPlannersBinding
import com.example.customweatherapp.main.toDayMonth
import com.example.customweatherapp.model.plan.ListPlans
import com.example.customweatherapp.model.plan.PlanWeather

class PlansAdapter(var listPlans:ListPlans):RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {
    inner class PlansViewHolder(val binding: ItemPlannersBinding):ViewHolder(binding.root){
        fun bind(plan:PlanWeather){
            binding.tvDatePlan.text = plan.date.toDayMonth()
            binding.tvHourPlan.text = "${plan.hour}:${plan.minutes} ${plan.time}"
            binding.tvTitlePlan.text = plan.title
            binding.tvTemperaturePlan.text = "${plan.temperature}°C"
            binding.imgIconPlan.load("https://openweathermap.org/img/wn/04d@2x.png")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        val binding = ItemPlannersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlansViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        holder.bind(listPlans.get(position))
    }

    override fun getItemCount(): Int = listPlans.size
}