package com.example.testforcoronaapp

import android.Manifest
import android.app.job.JobParameters
import android.app.job.JobService;
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*

class LocationJobService : JobService() {

    lateinit var locationManager : LocationManager
    lateinit var location: Location

    private val TAG = "ExampleJobService"
    private val jobCancelled = false

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job started");
        doBackgroundWork(params)

        return true
    }


    private fun doBackgroundWork(params: JobParameters) {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!

            reverseGeocode(location)
        }




    }

    private fun reverseGeocode(location: Location) {

        // Ich lasse mir meine Position einfach auf der Konsole ausgeben..
        // Im Emulator konnt ihr bei den 3 Punkten ganz unten (...) die Location ändern und wenn ihr das macht, dann ändert sich auch der output auf der Console

        val gc = Geocoder( this, Locale.getDefault())
        val addresses = gc.getFromLocation(location.latitude, location.longitude,1)
        val address  = addresses[0]

        Log.d(TAG, "Request ")
        Log.d("Meine Location ist: ", " ${address.adminArea}")
        Log.d("Meine Location ist: ", " ${address.thoroughfare}")
        Log.d("Meine Location ist: ", " ${address.featureName}")
        Log.d("Meine Location ist: ", " ${address.postalCode}")
        Log.d("Meine Location ist: ", " ${address.subLocality}")
        Log.d("Meine Location ist: ", " ${address.locality}")

    }


    override fun onStopJob(params: JobParameters): Boolean {

        return true
    }

}