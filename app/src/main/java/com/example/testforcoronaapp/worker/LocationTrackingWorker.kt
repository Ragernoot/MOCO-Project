package com.example.testforcoronaapp.worker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.Looper
import android.os.Looper.loop
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testforcoronaapp.R
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_1_ID
import com.example.testforcoronaapp.utils.SomeAlgorithms
import com.google.android.gms.location.*
import java.lang.Exception
import java.util.*
import kotlin.concurrent.thread


class LocationTrackingWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val TAG = "LocationTrackingWorker"
    private var isGranted = false
    private val csvList = SomeAlgorithms().readCSV(context)

    val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "AppDatabase"
    ).build()

    val districtDAO = database.districtDAO()
    val stateDAO = database.stateDAO()

    override fun doWork(): Result {

        val success = doBackgroundWork(applicationContext)

        if (success) {
            Log.e(TAG, "WORKER: SUCCESS")
            return Result.success()
        } else {
            Log.e(TAG, "WORKER: FAILURE")
            return Result.failure()
        }
    }



    private fun doBackgroundWork(context: Context): Boolean {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isGranted = true
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if(isGranted){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { newLocation ->

                val address = reverseGeocode(newLocation)

                for (csvElement in csvList){
                    if(address.postalCode == csvElement.csvPLZ){
                        val agsForDistrict = csvElement.csvAGS.slice(0..4)

                        //val district = districtDAO.findDistrict(agsForDistrict)

                        //Log.e(TAG, "doBackgroundWork: ${district.cases}")

                    }
                }

            }
        }

        // Log.e(TAG, "doBackgroundWork: ${csvElement.csvAGS.slice(0..4)}")

        val notification = NotificationCompat.Builder(context, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle("Worker")
            .setContentText("This Worker is working properly")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setNotificationSilent()
            .build()

        notificationManagerCompat.notify(1, notification)

        return true
    }

    private fun reverseGeocode(location: Location) : Address {
        val gc = Geocoder(applicationContext, Locale.getDefault())
        return gc.getFromLocation(location.latitude, location.longitude, 2)[0]
    }
}