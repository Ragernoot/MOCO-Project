package com.example.testforcoronaapp.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import com.example.testforcoronaapp.apis.CoronaDataService
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StateDAO
import com.example.testforcoronaapp.model.room.state.StatesData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.utils.SomeAlgorithms
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class DataServiceViewModel (private val repository: Repository, application : Application)  : AndroidViewModel(application) {

    private var stateDAO: StateDAO
    private var districtDAO: DistrictDAO

    init {
        val context = application.applicationContext
        val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "AppDatabase"
        ).build()

        stateDAO = database.stateDAO()
        districtDAO = database.districtDAO()
    }


    private val gson = Gson()
    private var listOfJSONObjectDistrict = mutableListOf<JSONObject>()
    private var listOfJSONObjectStates = mutableListOf<JSONObject>()

    private var listOfDistrictObjects = mutableListOf<DistrictData>()
    private var listOfStatesObjects = mutableListOf<StatesData>()


    fun loadDataToRoom() {
        viewModelScope.launch {
            //val statesResponse = CoronaDataService().getStates()
            val statesResponse = repository.getStatesDataRepo()
            Log.e(TAG, "loadDataToRoom: ${statesResponse.body()?.length}"  )
            convertStatesToJavaObject(statesResponse)

            for (ele in listOfStatesObjects) {
                stateDAO.insert(ele)
            }

            val districtResponse = repository.getDistrictDataRepo()
            Log.e(TAG, "loadDataToRoom: ${districtResponse.body()?.length}"  )
            convertDistrictsToJavaObject(districtResponse)

            for (ele in listOfDistrictObjects) {
                districtDAO.insert(ele)
            }
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

    private fun convertStatesToJavaObjectTest(statesResponse: String?) {
        val json = JSONObject(statesResponse!!)
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