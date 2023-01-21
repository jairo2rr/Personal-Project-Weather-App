package com.example.customweatherapp.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentExplorerBinding
import com.example.customweatherapp.model.City
import com.example.customweatherapp.model.service.WeatherDbClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ExplorerFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentExplorerBinding
    private lateinit var listCities:MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        listCities = mutableListOf("Peru","Argentina","Colombia","Paraguay")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplorerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(textCity: String?): Boolean {
                filterCities(textCity)
                return true
            }
        })
    }
    private fun filterCities(textCity: String?) {
        val apikey = getString(R.string.api_key)
        var newList = mutableListOf<String>()
        if(textCity != null){
            lifecycleScope.launch(Dispatchers.IO) {
                val citiesLocalized = WeatherDbClient.service.getCities(city = textCity, apikey = apikey)
                if(citiesLocalized != null){
                    citiesLocalized.forEach {
                            city -> println("City: ${city.name}, Country: ${city.country}")
                    }
                }else{
                    Log.d("Citiess","Errorrrrr!")
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExplorerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}