package com.example.testforcoronaapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.testforcoronaapp.R.id.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    // So bekommt man die Location.. Background Service fehlt noch, damit immer wieder im Hintergrund abgefragt und dann evaluiert wird,
    // um die Daten zu ändern die angezeigt werden

    lateinit var locationManager : LocationManager
    lateinit var location: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
           ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 111)


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!

        val locationListener = LocationListener { location -> reverseGeocode(location) }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100.2f, locationListener)


        // BOTTOM NAVIGATION
        val bottomNav = findViewById<BottomNavigationView>(bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(fragment_container, HomeFragment()).commit()
    }


    private fun reverseGeocode(location: Location) {

        // Ich lasse mir meine Position einfach auf der Konsole ausgeben..
        // Im Emulator konnt ihr bei den 3 Punkten ganz unten (...) die Location ändern und wenn ihr das macht, dann ändert sich auch der output auf der Console

        val gc = Geocoder( this, Locale.getDefault())
        val addresses = gc.getFromLocation(location.latitude, location.longitude,2)
        val address  = addresses[0]

        Log.d("Meine Location ist: ", " ${address.adminArea}")
        Log.d("Meine Location ist: ", " ${address.thoroughfare}")
        Log.d("Meine Location ist: ", " ${address.featureName}")
        Log.d("Meine Location ist: ", " ${address.postalCode}")
        Log.d("Meine Location ist: ", " ${address.phone}")
        Log.d("Meine Location ist: ", " ${address.locality}")


    }


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {

        val selectedFragment : Fragment = when(it.itemId){
            nav_home ->  HomeFragment()
            nav_map -> MapFragment()
            nav_settings -> SettingsFragment()
            else -> HomeFragment()
        }

        supportFragmentManager.beginTransaction().replace(fragment_container, selectedFragment).commit()

        return@OnNavigationItemSelectedListener true
    }

}
