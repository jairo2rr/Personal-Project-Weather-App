package com.example.customweatherapp.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.ActivityMainBinding
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.model.PrincipalData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /*Default values for lat and lon*/
    private var latitude = -12.04318
    private var longitude = -77.02824

    private var locationOff: Boolean = false

    private var requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            setRealLatLon(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        bottomNav = binding.bottomNavigationView

        bottomNav.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.home -> {
                    initHomeFragment()
                    true
                }
                R.id.planning -> {
                    initPlanningFragment()
                    true
                }
                R.id.explorer -> {
                    initExplorerFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun initPlanningFragment() {
        val planningFragment = PlanningFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentReplacer.id, planningFragment)
            .commit()
    }

    private fun initExplorerFragment() {
        val explorerFragment = ExplorerFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentReplacer.id, explorerFragment)
            .commit()
    }

    private fun initHomeFragment() {
        var data: PrincipalData? = null
        if (locationOff) {
            data = prefers.getData()
        }
        if (data != null) {
            latitude = data.city.coord.lat
            longitude = data.city.coord.lon
        }
        val bundle = Bundle().apply {
            putDouble("LATITUDE", latitude)
            putDouble("LONGITUDE", longitude)
        }
        val homeFragment = HomeFragment().apply { arguments = bundle }
        supportFragmentManager.beginTransaction().replace(binding.fragmentReplacer.id, homeFragment)
            .commit()
    }

    private fun setRealLatLon(isLocationGranted: Boolean) {
        when (isLocationGranted) {
            true -> {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                        fusedLocationClient.lastLocation.addOnCompleteListener {

                            if (it.result != null) {

                                latitude = it.result!!.latitude
                                longitude = it.result!!.longitude
                            } else {
                                locationOff = true
                            }
                            Log.d(
                                "debugLocation",
                                "Lat: ${it.result?.latitude}, Lon: ${it.result?.longitude}"
                            )
                            initHomeFragment()
                        }
                    }
                    return
                }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                Toast.makeText(
                    this,
                    "Al denegar el permiso no se puede acceder a su ubicacion actual",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            else -> {
                Toast.makeText(
                    this,
                    "Para activar el permiso de localizacion, tiene que ir a configuracion.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
    }
}