package com.example.testforcoronaapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_1_ID
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_2_ID
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_3_ID
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_4_ID
import com.example.testforcoronaapp.worker.FromGetToRoomWorker
import com.example.testforcoronaapp.worker.LocationTrackingWorker
import java.util.concurrent.TimeUnit

class ApplicationInit : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
        scheduleDataWorker(applicationContext)
        scheduleLocationWorker(applicationContext)
    }

    private fun createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel_1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is my first Channel"

            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                "Channel_2",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.description = "This is my second Channel"

            val channel3 = NotificationChannel(
                    CHANNEL_3_ID,
                    "Channel_3",
                    NotificationManager.IMPORTANCE_HIGH
            )
            channel2.description = "This is my third Channel"

            val channel4 = NotificationChannel(
                    CHANNEL_4_ID,
                    "Channel_4",
                    NotificationManager.IMPORTANCE_HIGH
            )
            channel2.description = "This is my fourth Channel"

            val manager : NotificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
            manager.createNotificationChannel(channel3)
            manager.createNotificationChannel(channel4)
        }
    }


    private fun scheduleLocationWorker(context : Context){

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val locationWorker =
            PeriodicWorkRequestBuilder<LocationTrackingWorker>(15, TimeUnit.MINUTES)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .setConstraints(constraints)
                    .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("LocationWorker", ExistingPeriodicWorkPolicy.KEEP, locationWorker)

    }

    private fun scheduleDataWorker(context : Context){
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val dataWorker =
            PeriodicWorkRequestBuilder<FromGetToRoomWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("DataWorker", ExistingPeriodicWorkPolicy.KEEP, dataWorker)
    }
}