package com.example.testforcoronaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testforcoronaapp.R.id.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(fragment_container, HomeFragment()).commit()
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
