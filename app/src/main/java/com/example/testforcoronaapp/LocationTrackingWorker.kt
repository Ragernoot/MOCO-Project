package com.example.testforcoronaapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testforcoronaapp.utils.SomeAlgorithms
import java.util.*

class LocationTrackingWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG = "LocationTrackingWorker"

    private lateinit var locationManager : LocationManager
    private lateinit var myLocation: Location
    private var address: Address = Address(Locale("ger", "DE"))

    var isGPSEnabled = false

    override fun doWork(): Result {

        val succes = doBackgroundWork(applicationContext)

        Log.e(TAG, "WORKER: HIER KOMME ICH REIN" )

        if(succes){
            Log.e(TAG, "WORKER: SUCCESS" )
            return Result.success()
        } else {
            Log.e(TAG, "WORKER: FAILURE" )
            return Result.failure()
        }
    }

    private fun doBackgroundWork(context : Context) : Boolean {

//        thread(start = true){
//            kotlin.run {
//                for (i in 0..10){
//                    Log.d(TAG, "run: $i")
//
//                    try {
//                        Thread.sleep(1000)
//                    } catch (e : InterruptedException){
//                        e.printStackTrace()
//                    }
//                }
//
//                Log.d(TAG, "Job finished")
//
//            }
//        }

        val csvList = SomeAlgorithms().readCSV(context)

        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager.isProviderEnabled(GPS_PROVIDER)

        if (!isGPSEnabled) {
            Toast.makeText(context, "Location is not active", Toast.LENGTH_SHORT ).show()
        } else {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled) {
                    Log.d("GPS", "GPS Enabled")

                    myLocation = if(locationManager.getLastKnownLocation(GPS_PROVIDER) != null){ 
                        locationManager.getLastKnownLocation(GPS_PROVIDER)!! 
                    } else {
                        Location(GPS_PROVIDER)
                    }
                }
                address = reverseGeocode(myLocation)
            }
        }

        for (element in csvList) {

            if (element.csvPLZ == address.postalCode) {

            }

        }




        return true
    }


    private fun reverseGeocode(location: Location) : Address {

        // Ich lasse mir meine Position einfach auf der Konsole ausgeben..
        // Im Emulator konnt ihr bei den 3 Punkten ganz unten (...) die Location ändern und wenn ihr das macht, dann ändert sich auch der output auf der Console

        val gc = Geocoder(applicationContext, Locale.getDefault())
        val addresses = gc.getFromLocation(location.latitude, location.longitude, 1)
        if(addresses.size != 0) {
            address = addresses[0]
       }

        return address
    }
}