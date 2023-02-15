package com.example.customweatherapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.customweatherapp.databinding.ItemCityFoundBinding
import com.example.customweatherapp.data.model.explorar.CityItemModel
import com.example.customweatherapp.domain.model.CityItem

class CityLocalizedAdapter(var listCities: List<CityItem>, private val onItemClick: (CityItem)->Unit):RecyclerView.Adapter<CityLocalizedAdapter.CityHolder>() {

    lateinit var bindItem:ItemCityFoundBinding

    inner class CityHolder(
        private val binding: ItemCityFoundBinding
    ):ViewHolder(binding.root){
        fun bind(cityLocalized: CityItem){
            binding.tvCity.text = cityLocalized.name
            binding.tvCountry.text = cityLocalized.country
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        bindItem = ItemCityFoundBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CityHolder(bindItem)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        if(listCities!=null){
            holder.bind(listCities!![position])
            holder.itemView.setOnClickListener { onItemClick(listCities!![position]) }
        }
    }

    override fun getItemCount():Int = listCities?.size ?: 0
}