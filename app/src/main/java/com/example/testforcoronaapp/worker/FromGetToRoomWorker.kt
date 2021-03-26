package com.example.testforcoronaapp.worker

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testforcoronaapp.R
import com.example.testforcoronaapp.apis.CoronaDataService
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StatesData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.utils.Constants
import com.example.testforcoronaapp.utils.SomeAlgorithms
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response
import kotlin.concurrent.thread

class FromGetToRoomWorker (context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private var notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)

    private val repository = Repository()
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "RoomDatabase"
    ).build()

    val stateDAO = db.stateDAO()
    val districtDAO = db.districtDAO()

    private val gson = Gson()
    private var listOfJSONObjectDistrict = mutableListOf<JSONObject>()
    private var listOfJSONObjectStates = mutableListOf<JSONObject>()

    private var listOfDistrictObjects = mutableListOf<DistrictData>()
    private var listOfStatesObjects = mutableListOf<StatesData>()


    override suspend fun doWork(): Result {
        // #################################
        // Funktioniert nicht

        val test = repository.getStatesDataRepo()
        Log.e(TAG, "doWork: ${test.body()}" )
        convertStatesToJavaObject(test)

        for (ele in listOfStatesObjects) {
            stateDAO.insert(ele)
        }
        // #################################

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

        val notification = NotificationCompat.Builder(context, Constants.CHANNEL_2_ID)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle("Worker DATA")
            .setContentText("This Worker is working properly")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setNotificationSilent()
            .build()

        notificationManagerCompat.notify(2, notification)

        return true
    }

    private fun convertStatesToJavaObjectTest(statesResponse: String) {
        val json = JSONObject(statesResponse)
        val data = json.getJSONObject("data")
        val allKeys = data.keys()
        allKeys.forEach { listOfJSONObjectStates.add(data.getJSONObject(it)) }

        allKeys.forEach {
            Log.d(TAG, "convertStatesToJavaObjectTest: $it")
        }
        
        for (listItem in listOfJSONObjectStates) {
            
            val jsonObjectString = listItem.toString()
            val newString = SomeAlgorithms().stringChanger(jsonObjectString)
            val statesData = gson.fromJson(newString, StatesData::class.java)
            listOfStatesObjects.add(statesData)
        }
    }

    private fun convertDistrictsToJavaObject(districtResponse : Response<String>) {
        val jsonDataString = districtResponse.body()
        val json = JSONObject(jsonDataString!!)
        val data = json.getJSONObject("data")
        val allKeys = data.keys()
        allKeys.forEach { listOfJSONObjectDistrict.add(data.getJSONObject(it)) }

        for (listItem in listOfJSONObjectDistrict) {
            val jsonObjectString = listItem.toString()
            val newString = SomeAlgorithms().stringChanger(jsonObjectString)
            val districtData = gson.fromJson(newString, DistrictData::class.java)
            listOfDistrictObjects.add(districtData)
        }
    }

    private fun convertStatesToJavaObject(statesResponse: Response<String>) {
        val jsonDataString = statesResponse.body()
        val json = JSONObject(jsonDataString!!)
        val data = json.getJSONObject("data")
        val allKeys = data.keys()
        allKeys.forEach { listOfJSONObjectStates.add(data.getJSONObject(it)) }

        for (listItem in listOfJSONObjectStates) {
            val jsonObjectString = listItem.toString()
            val newString = SomeAlgorithms().stringChanger(jsonObjectString)
            val statesData = gson.fromJson(newString, StatesData::class.java)
            listOfStatesObjects.add(statesData)
        }
    }

}