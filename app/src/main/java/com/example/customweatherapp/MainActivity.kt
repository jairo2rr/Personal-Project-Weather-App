package com.example.customweatherapp

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.customweatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav:BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /*Default values lat and lon*/
    private var latitude = -8.11599
    private var longitude = -79.02998

    private var permLocationAcepted = false

    private var requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted -> permLocationAcepted = isGranted
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
        setRealLatLon(permLocationAcepted)
        supportFragmentManager.beginTransaction().replace(binding.fragmentReplacer.id,HomeFragment()).commit()
        bottomNav = binding.bottomNavigationView



        bottomNav.setOnItemSelectedListener { menu->
            when(menu.itemId){
                R.id.home-> {
                    val bundle = Bundle().apply {
                        putDouble("LATITUDE",latitude)
                        putDouble("LONGITUDE",longitude)
                    }
                    val homeFragment = HomeFragment().apply{ arguments = bundle }
                    supportFragmentManager.beginTransaction().replace(binding.fragmentReplacer.id,homeFragment).commit()
                    true
                }
                else->false
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setRealLatLon(isLocationGranted: Boolean) {
        Log.d("LATITUDEEE","Latitude2: $latitude\n Longitude2: $longitude")
        if(isLocationGranted){
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if(it.result.latitude != null){
                    latitude = it.result.latitude
                    longitude = it.result.longitude
                }
            }
        }

    }
}