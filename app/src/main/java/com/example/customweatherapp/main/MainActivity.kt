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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /*Default values lat and lon*/
    private var latitude = -12.04318
    private var longitude = -77.02824

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
                else -> false
            }
        }
    }

    private fun initHomeFragment() {
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
                    lifecycleScope.launch(Dispatchers.IO){
                        fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                        fusedLocationClient.lastLocation.addOnCompleteListener {
                            if (it.result != null) {
                                latitude = it.result.latitude
                                longitude = it.result.longitude
                            }
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