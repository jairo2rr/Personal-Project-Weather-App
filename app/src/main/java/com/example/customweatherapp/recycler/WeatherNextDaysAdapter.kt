package com.example.customweatherapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.ItemNextDaysBinding
import com.example.customweatherapp.model.WeatherPerDay

class WeatherNextDaysAdapter(
    var listWeather: List<WeatherPerDay>,
    private val type: String,
    private val onItemClick: (WeatherPerDay) -> Unit
) : RecyclerView.Adapter<NextWeatherHolder>() {

    private var lastHolder: NextWeatherHolder? = null
    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextWeatherHolder {
        val binding =
            ItemNextDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NextWeatherHolder(binding, type)
    }

    override fun onBindViewHolder(holder: NextWeatherHolder, position: Int) {
        holder.bind(listWeather[position])

        holder.itemView.setOnClickListener {

            onItemClick(listWeather[position])
            updatedItemUI(holder, position)
        }
    }

    private fun updatedItemUI(holder: NextWeatherHolder, position: Int) {
        val itemBinding = holder.binding
        itemBinding.llItemNextDays.isSelected = lastPosition!=position
        /*itemBinding.llItemNextDays.setBackgroundResource(R.drawable.bg_item_selected)
        if(lastPosition == position) return*/
        if (lastPosition != -1) {
            lastHolder!!.binding.llItemNextDays.isSelected = false
//            lastHolder!!.binding.llItemNextDays.setBackgroundResource(R.drawable.bg_card_today)
        }
        lastHolder = holder
        lastPosition = position
    }

    override fun getItemCount(): Int = listWeather.size
}