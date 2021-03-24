package com.example.testforcoronaapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_1_ID
import com.example.testforcoronaapp.utils.Constants.Companion.CHANNEL_2_ID

class ApplicationInit : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    fun createNotificationChannels(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel_Test_1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is my first Channel"

            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                "Channel_Test_2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is my first Channel"

            val manager : NotificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)

        }

    }
}