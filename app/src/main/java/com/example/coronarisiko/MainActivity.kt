package com.example.coronarisiko

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.coronarisiko.R.id.*
import com.example.coronarisiko.fragments.HomeFragment
import com.example.coronarisiko.fragments.MapFragment
import com.example.coronarisiko.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentMap: Fragment = MapFragment()
//    private val fragmentSettings: Fragment = SettingsFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager
    var active = fragmentHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread (start = true){
            kotlin.run {
                Thread.sleep(1000)
//                fragmentManager.beginTransaction().add(fragment_container, fragmentSettings, "3").hide(fragmentSettings).commit()
                fragmentManager.beginTransaction().add(fragment_container, fragmentMap, "2").hide(fragmentMap).commit()
                fragmentManager.beginTransaction().add(fragment_container,fragmentHome, "1").commit()
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),111)
        }

        // BOTTOM NAVIGATION
        val bottomNav = findViewById<BottomNavigationView>(bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
    }


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            nav_home -> {
                fragmentManager.beginTransaction().hide(active).show(fragmentHome).commit()
                active = fragmentHome
                return@OnNavigationItemSelectedListener true}
            nav_map -> {
                fragmentManager.beginTransaction().hide(active).show(fragmentMap).commit()
                active = fragmentMap
                return@OnNavigationItemSelectedListener true}
//            nav_settings -> {
//                fragmentManager.beginTransaction().hide(active).show(fragmentSettings).commit()
//                active = fragmentSettings
//                return@OnNavigationItemSelectedListener true}
        }
        false
    }

}
