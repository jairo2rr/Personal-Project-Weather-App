package com.example.customweatherapp.main

import android.content.Context
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
import com.example.customweatherapp.model.explorar.CityLocalized
import com.example.customweatherapp.model.explorar.CityLocalizedItem
import com.example.customweatherapp.model.service.WeatherDbClient
import com.example.customweatherapp.recycler.CityLocalizedAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class ExplorerFragment : Fragment() {

    private lateinit var binding:FragmentExplorerBinding
    private lateinit var listCities:CityLocalized
    private lateinit var adapter:CityLocalizedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listCities = CityLocalized()
        adapter = CityLocalizedAdapter(listCities)
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

        binding.recyclerView2.adapter = adapter
        /*Probando corrutina*/
        /*binding.btnPruebaCorrutina.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val numberMystery = withContext(Dispatchers.IO){getMysteryValue()}
                context?.toast("This number: $numberMystery")
            }
        }*/
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("getTextSubmit","This: $query")
                return false
            }

            override fun onQueryTextChange(textCity: String?): Boolean {
                Log.d("getTextCity","This: $textCity")
                filterCities(textCity)
                return true
            }
        })
    }

    /*Funcion de prueba*/
    private fun getMysteryValue():Int{
        Thread.sleep(2000)
        return 0
    }

    fun Context.toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun filterCities(textCity: String?) {
        val apikey = getString(R.string.api_key)
        var newList = mutableListOf<String>()
        if(textCity==""){
            adapter.listCities = CityLocalized()
            adapter.notifyDataSetChanged()
        }else if(textCity != null){
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d("getText","Data:${textCity.toMediaTypeOrNull()}")
                val citiesLocalized = WeatherDbClient.service.getCities(city = textCity!!, apikey = apikey)
                if(citiesLocalized != null){
                    withContext(Dispatchers.Main){
                        adapter.listCities = citiesLocalized
                        adapter.notifyDataSetChanged()
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
                    //The arguments with their values
                }
            }
    }
}