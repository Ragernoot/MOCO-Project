package com.example.testforcoronaapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testforcoronaapp.ApplicationInit
import com.example.testforcoronaapp.R
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_1_ID
import java.util.*
import kotlin.concurrent.thread


class LocationTrackingWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val TAG = "LocationTrackingWorker"

    val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "AppDatabase"
    ).build()

    val districtDAO = database.districtDAO()
    val stateDAO = database.stateDAO()

    override fun doWork(): Result {
        val succes = doBackgroundWork(applicationContext)

        if (succes) {
            Log.e(TAG, "WORKER: SUCCESS")
            return Result.success()
        } else {
            Log.e(TAG, "WORKER: FAILURE")
            return Result.failure()
        }
    }

    private fun doBackgroundWork(context: Context): Boolean {
        //justAThread()

        val notification = NotificationCompat.Builder(context, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle("Worker")
            .setContentText("This Worker is working properly")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        notificationManagerCompat.notify(1, notification)

        return true
    }


    private fun reverseGeocode(location: Location?) {
        val gc = Geocoder(applicationContext, Locale.getDefault())
        if (location != null) {
            val address = gc.getFromLocation(location.latitude, location.longitude, 2)[0]
        }
    }


    fun justAThread(){
        thread(start = true){
            kotlin.run {
                for (i in 0..10){
                    Log.d(TAG, "run: $i")

                    try {
                        Thread.sleep(1000)
                    } catch (e : InterruptedException){
                        e.printStackTrace()
                    }
                }

                Log.d(TAG, "Job finished")

            }
        }
    }

}