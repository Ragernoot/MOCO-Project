package com.example.coronarisiko.worker

import android.app.PendingIntent
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coronarisiko.R
import com.example.coronarisiko.ResultActivity
import com.example.coronarisiko.model.room.AppDatabase
import com.example.coronarisiko.model.room.currentDistrict.CurrentDistrict
import com.example.coronarisiko.utils.Converters
import com.example.coronarisiko.model.room.district.DistrictData
import com.example.coronarisiko.model.room.lastDistrict.LastDistrict
import com.example.coronarisiko.utils.Constants.Companion.CHANNEL_1_ID
import com.example.coronarisiko.utils.Constants.Companion.CHANNEL_3_ID
import com.example.coronarisiko.utils.Constants.Companion.DATABASE_NAME
import com.example.coronarisiko.utils.SomeAlgorithms
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.NonCancellable.cancel
import java.util.*
import kotlin.concurrent.thread


class LocationTrackingWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val TAG = "LocationTrackingWorker"
    private var isGranted = false
    private val csvList = SomeAlgorithms().readCSV(context)

    private var curDistrict = DistrictData(
            "00000",
            "Default",
            "Default",
            "Default",
            0,
            0,
            0,
            0,
            0,
            "Default",
            0,
            0.0,
            0.0
    )

    private var lastDistrict = LastDistrict(
            "00000",
            "Default",
            "Default",
            "Default",
            0,
            0,
            0,
            0,
            0,
            "Default",
            0,
            0.0,
            0.0
    )

    val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java, DATABASE_NAME
    ).build()

    private val districtDAO = database.districtDAO()
    private val stateDAO = database.stateDAO()
    private val lastDistrictDAO = database.lastDistrictDAO()
    private val currentDistrictDAO = database.currentDistrictDAO()

    override suspend fun doWork(): Result {

        val success = doBackgroundWork(applicationContext)

        database.close()

        return if (success) {
            Log.e(TAG, "WORKER: SUCCESS")
            Result.success()
        } else {
            Log.e(TAG, "WORKER: FAILURE")
            Result.failure()
        }
    }



    private fun doBackgroundWork(context: Context): Boolean {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isGranted = true
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (isGranted) {
            // lastLocation ist vermutlich nicht das was wir wollen, weil Änderungen der Location nicht beachtet werden...
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token.onCanceledRequested {
                OnTokenCanceledListener {
                    // TODO Do something when token is canceled
                }
            }).addOnSuccessListener { newLocation ->

                val address = reverseGeocode(newLocation)
                Log.e(TAG, address.postalCode)
                for (csvElement in csvList) {
                    if (address.postalCode == csvElement.csvPLZ) {
                        val agsForDistrict = csvElement.csvAGS.slice(0..4)

                        thread(start = true) {
                            kotlin.run {
                                curDistrict = districtDAO.findDistrict(agsForDistrict)
                                val currentDistrict = Converters().fromDistrictToLastDistrict(curDistrict)

                                if (lastDistrictDAO.isEmpty() == 0) {
                                    lastDistrictDAO.insert(currentDistrict)
                                } else {
                                    lastDistrict = lastDistrictDAO.getLastDistrict()

                                    if (lastDistrict != currentDistrict) {
                                        if (lastDistrict.cases < currentDistrict.cases) {

                                            val currentDistrictAsCurrent = Converters().fromDistrictToCurrentDistrict(curDistrict)
                                            val lastCurrentDistrict = currentDistrictDAO.getLastCurrentDistrict()

                                            if (currentDistrictDAO.isEmpty() == 0) {
                                                currentDistrictDAO.insert(currentDistrictAsCurrent)
                                            } else {
                                                currentDistrictDAO.delete(lastCurrentDistrict)
                                                currentDistrictDAO.insert(currentDistrictAsCurrent)
                                            }

                                            val intent = Intent(context, ResultActivity::class.java).apply {
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            }

                                            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                                            val notification = NotificationCompat.Builder(context, CHANNEL_3_ID)
                                                    .setSmallIcon(R.drawable.ic_round_warning_24)
                                                    .setContentTitle("Achtung Corona! Erhöhte Fallzahlen")
                                                    .setContentText("Klicke mich um mehr zu erfahren..")
                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                    .setNotificationSilent()
                                                    .setContentIntent(pendingIntent)
                                                    .setAutoCancel(true)
                                                    .build()

                                            notificationManagerCompat.notify(3, notification)
                                        }
                                    }

                                    Thread.sleep(14 * 60 * 1000)
                                    lastDistrictDAO.delete(lastDistrict)
                                    lastDistrictDAO.insert(currentDistrict)

                                }

                                Log.e(TAG, "doBackgroundWork: ${currentDistrict.name}")

                            }
                        }
                    }
                }
            }
        }


//        val intent = Intent(context, ResultActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val notification = NotificationCompat.Builder(context, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_round_warning_24)
//                .setContentTitle("Achtung Corona! Erhöhte Fallzahlen außerhalb")
//                .setContentText("Klicke mich um mehr zu erfahren..")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setNotificationSilent()
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build()
//
//        notificationManagerCompat.notify(1, notification)

        return true
    }


    private fun reverseGeocode(location: Location) : Address {
        val gc = Geocoder(applicationContext, Locale.getDefault())
        return gc.getFromLocation(location.latitude, location.longitude, 2)[0]
    }
}