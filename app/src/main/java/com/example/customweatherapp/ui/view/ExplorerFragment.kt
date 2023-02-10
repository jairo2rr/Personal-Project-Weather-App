package com.example.customweatherapp.ui.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.customweatherapp.R
import com.example.customweatherapp.data.model.PrincipalData
import com.example.customweatherapp.data.model.explorar.CityLocalized
import com.example.customweatherapp.databinding.FragmentExplorerBinding
import com.example.customweatherapp.domain.GetCitiesUC
import com.example.customweatherapp.domain.GetPrincipalDataUC
import com.example.customweatherapp.recycler.CityLocalizedAdapter
import com.example.customweatherapp.ui.viewmodel.ExplorerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ExplorerFragment : Fragment() {

    private lateinit var binding: FragmentExplorerBinding
    private lateinit var adapter: CityLocalizedAdapter

    @Inject
    lateinit var getPrincipalDataUC: GetPrincipalDataUC
    @Inject
    lateinit var getCitiesUC: GetCitiesUC

    private val explorerViewModel: ExplorerViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CityLocalizedAdapter(CityLocalized()) { city ->
            lifecycleScope.launch(Dispatchers.IO) {
                val data = getPrincipalDataUC(
                    city.lat,
                    city.lon,
                    getString(R.string.api_key)
                )
                initWeatherActivity(data)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initWeatherActivity(data: PrincipalData?) {
        if (data == null) {
            Toast.makeText(context, "Error de datos", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(context, WeatherDayActivity::class.java).apply {
            putExtra("data", data)
            putExtra("dt_txt", data.list[0].dt_txt)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplorerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apikey = getString(R.string.api_key)
        binding.recyclerView2.adapter = adapter
        explorerViewModel.listCities.observe(viewLifecycleOwner) {
            adapter.listCities = it
            adapter.notifyDataSetChanged()
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(textCity: String?): Boolean {
//                filterCities(textCity)
                if (textCity != "" && textCity != null)
                    explorerViewModel.searchCity(textCity,apikey)
                return true
            }
        })
    }


    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun filterCities(textCity: String?) {
        val apikey = getString(R.string.api_key)
        if (textCity == "") {
            adapter.listCities = CityLocalized()
            adapter.notifyDataSetChanged()
        } else if (textCity != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                val citiesLocalized =
                    getCitiesUC(textCity, apikey)
                withContext(Dispatchers.Main) {
                    adapter.listCities = citiesLocalized
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}