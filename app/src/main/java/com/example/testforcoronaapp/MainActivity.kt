package com.example.testforcoronaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.R.id.*
import com.example.testforcoronaapp.fragments.HomeFragment
import com.example.testforcoronaapp.fragments.MapFragment
import com.example.testforcoronaapp.fragments.SettingsFragment
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewModelFactorys.DataServiceViewModelFactory
import com.example.testforcoronaapp.viewmodels.DataServiceViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),111)
        }

        // BOTTOM NAVIGATION
        val bottomNav = findViewById<BottomNavigationView>(bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        delayHomeFragment()
    }

    private fun delayHomeFragment(){
        thread (start = true){
            kotlin.run {
                Thread.sleep(1000)
                supportFragmentManager.beginTransaction().replace(fragment_container, HomeFragment()).commit()
            }
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            nav_home -> {
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true}
            nav_map -> {
                replaceFragment(MapFragment())

                return@OnNavigationItemSelectedListener true}
            nav_settings -> {
                replaceFragment(SettingsFragment())
                return@OnNavigationItemSelectedListener true}
        }
        false
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragment_container, fragment).commit()
    }

}
