package com.example.customweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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

    private var requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted -> setRealLatLon(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        initHomeFragment()
        bottomNav = binding.bottomNavigationView

        bottomNav.setOnItemSelectedListener { menu->
            when(menu.itemId){
                R.id.home-> {
                    initHomeFragment()
                    true
                }
                else->false
            }
        }
    }

    private fun initHomeFragment() {
        val bundle = Bundle().apply {
            putDouble("LATITUDE",latitude)
            putDouble("LONGITUDE",longitude)
        }
        val homeFragment = HomeFragment().apply{ arguments = bundle }
        supportFragmentManager.beginTransaction().replace(binding.fragmentReplacer.id,homeFragment).commit()
    }

    private fun setRealLatLon(isLocationGranted: Boolean) {
        when(isLocationGranted){
            true ->{
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
                    fusedLocationClient.lastLocation.addOnCompleteListener {
                        if(it.result.latitude != null){
                            latitude = it.result.latitude
                            longitude = it.result.longitude
                        }
                    }
                    return
                }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)->{

            }
            else->{
                Toast.makeText(this,"Debe aceptar el permiso de ubicacion para obtener su ubicacion",Toast.LENGTH_SHORT).show()
            }
        }
    }
}