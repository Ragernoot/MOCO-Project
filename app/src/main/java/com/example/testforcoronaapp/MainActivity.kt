package com.example.testforcoronaapp

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler;
import android.content.ComponentName
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.testforcoronaapp.R.id.*
import com.example.testforcoronaapp.utils.Constants.Companion.LOCATION_JOB_ID
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var locationManager : LocationManager
    lateinit var location: Location
//    lateinit var homeFragment : HomeFragment
//    lateinit var mapFragment : MapFragment
//    lateinit var settingsFragment : SettingsFragment

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        homeFragment = HomeFragment()
//        mapFragment = MapFragment()
//        settingsFragment = SettingsFragment()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 111)
        }

        scheduleJob()

        // BOTTOM NAVIGATION
        val bottomNav = findViewById<BottomNavigationView>(bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(fragment_container, HomeFragment()).commit()

    }



    private fun scheduleJob() {

        val componentName = ComponentName(this, LocationJobService::class.java)
        val jobInfo = JobInfo.Builder(LOCATION_JOB_ID, componentName)
                .setPersisted(true)
                .setPeriodic(1000 * 60 * 15) // Standard: 15 Minuten
                .build()

        val scheduler =  getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(jobInfo)

        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed")
        }

    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {

        val selectedFragment : Fragment = when(it.itemId){
            nav_home -> HomeFragment()
            nav_map -> MapFragment()
            nav_settings -> SettingsFragment()
            else -> HomeFragment()
//            nav_home ->  homeFragment
//            nav_map -> mapFragment
//            nav_settings -> settingsFragment
//            else -> homeFragment
        }

        supportFragmentManager.beginTransaction().replace(fragment_container, selectedFragment).commit()

        return@OnNavigationItemSelectedListener true
    }

//    private fun reverseGeocode(location: Location) {
//
//        Log.d(TAG, "reverseGeocode: HHHHHHHHHHHHHH")
//        val gc = Geocoder(this, Locale.getDefault())
//        val addresses = gc.getFromLocation(location.latitude, location.longitude, 1)
//        val address = addresses[0]
//
//        Log.d(TAG, "${0}. Request")
//        Log.d("Meine Location ist: ", " ${address.adminArea}")
//        Log.d("Meine Location ist: ", " ${address.thoroughfare}")
//        Log.d("Meine Location ist: ", " ${address.postalCode}")
//        Log.d("Meine Location ist: ", " ${address.subLocality}")
//        Log.d("Meine Location ist: ", " ${address.locality}")
//
//    }


}
