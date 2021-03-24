package com.example.testforcoronaapp.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StatesData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.utils.SomeAlgorithms
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class DataServiceViewModel (private val repository: Repository, database: AppDatabase)  : ViewModel() {

    private val gson = Gson()
    private var listOfJSONObjectDistrict = mutableListOf<JSONObject>()
    private var listOfJSONObjectStates = mutableListOf<JSONObject>()

    private var listOfDistrictObjects = mutableListOf<DistrictData>()
    private var listOfStatesObjects = mutableListOf<StatesData>()

    val stateDAO = database.stateDAO()
    val districtDAO = database.districtDAO()

    fun loadDataToRoom() {
        viewModelScope.launch {

            val statesResponse = repository.getStatesDataRepo()
            convertStatesToJavaObject(statesResponse)

            for (ele in listOfStatesObjects) {
                stateDAO.insert(ele)
            }

            val districtResponse = repository.getDistrictDataRepo()
            convertDistrictsToJavaObject(districtResponse)

            for (ele in listOfDistrictObjects) {
                districtDAO.insert(ele)
            }
        }

    }


    // TODO TESTEREI
//    fun loadDataToRoom2(){
//        val statesResponse = repository.getStatesDataRepo()
//        convertStatesToJavaObject(statesResponse)
//        //stateDAO.insertAll(listOfStatesObjects.toTypedArray())
//
//        for (ele in listOfStatesObjects) {
//            stateDAO.insert(ele)
//        }
//
//        val districtResponse = repository.getDistrictDataRepo()
//        convertDistrictsToJavaObject(districtResponse)
//        //districtDAO.insertAll(listOfDistrictObjects.toTypedArray())
//
//        for (ele in listOfDistrictObjects) {
//            districtDAO.insert(ele)
//        }
//    }


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