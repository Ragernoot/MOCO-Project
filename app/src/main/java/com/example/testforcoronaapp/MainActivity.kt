package com.example.testforcoronaapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.testforcoronaapp.R.id.*
import com.example.testforcoronaapp.fragments.HomeFragment
import com.example.testforcoronaapp.fragments.MapFragment
import com.example.testforcoronaapp.fragments.SettingsFragment
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.DataServiceViewModelFactory
import com.example.testforcoronaapp.viewmodels.DataServiceViewModel
import com.example.testforcoronaapp.worker.LocationTrackingWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

//    lateinit var homeFragment : HomeFragment
//    lateinit var mapFragment : MapFragment
//    lateinit var settingsFragment : SettingsFragment

    private lateinit var viewModel: DataServiceViewModel

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()

        val repository = Repository()
        val viewModelFactory = DataServiceViewModelFactory(repository, database)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DataServiceViewModel::class.java)

        viewModel.loadDataToRoom()

//        homeFragment = HomeFragment()
//        mapFragment = MapFragment()
//        settingsFragment = SettingsFragment()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 111)
        }


        scheduleWorker(applicationContext)

        // BOTTOM NAVIGATION
        val bottomNav = findViewById<BottomNavigationView>(bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(fragment_container, HomeFragment()).commit()

    }

    private fun scheduleWorker(context : Context){

        val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

        val locationWorker =
                PeriodicWorkRequestBuilder<LocationTrackingWorker>(15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()

        WorkManager.getInstance(context).enqueue(locationWorker)
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

}
