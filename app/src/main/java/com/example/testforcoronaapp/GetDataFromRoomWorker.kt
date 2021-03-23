package com.example.testforcoronaapp

import android.content.Context
import androidx.room.Room
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testforcoronaapp.model.room.AppDatabase

class GetDataFromRoomWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "RoomDatabase"
    ).build()

    val statesDAO = db.stateDAO()
    val districtDAO = db.districtDAO()


    override fun doWork(): ListenableWorker.Result {


        return ListenableWorker.Result.success()
    }

}