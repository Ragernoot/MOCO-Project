package com.example.testforcoronaapp

import android.Manifest
import android.app.job.JobParameters
import android.app.job.JobService;
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*
import kotlin.concurrent.thread
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER

class LocationJobService : JobService() {

    lateinit var locationManager : LocationManager
    lateinit var myLocation: Location

    private val TAG = "ExampleJobService"
    private val jobCancelled = false

    var isGPSEnabled = false

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job started");
        doBackgroundWork(params)

        return true
    }


    private fun doBackgroundWork(params: JobParameters) {

        thread(start = true){
            kotlin.run {
                for (i in 0..100){
                    Log.d(TAG, "run: $i")

                    try {
                        Thread.sleep(1000)
                    } catch (e : InterruptedException){
                        e.printStackTrace()
                    }
                }

                Log.d(TAG, "Job finished")
                jobFinished(params, true)
            }
        }

        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager.isProviderEnabled(GPS_PROVIDER)

//        if (!isGPSEnabled) {
//            Toast.makeText(this, "Location is not active", Toast.LENGTH_SHORT ).show()
//        } else {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                if (isGPSEnabled) {
//                    Log.d("GPS", "GPS Enabled")
//                    myLocation = locationManager.getLastKnownLocation(GPS_PROVIDER)
//                }
//                reverseGeocode(myLocation)
//
//            }
//        }
    }

    private fun reverseGeocode(location: Location) {

        // Ich lasse mir meine Position einfach auf der Konsole ausgeben..
        // Im Emulator konnt ihr bei den 3 Punkten ganz unten (...) die Location ändern und wenn ihr das macht, dann ändert sich auch der output auf der Console

        val gc = Geocoder(this, Locale.getDefault())
        val addresses = gc.getFromLocation(location.latitude, location.longitude, 1)
        val address = addresses[0]

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