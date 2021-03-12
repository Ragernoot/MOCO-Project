package com.example.testforcoronaapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.districtModel.DistrictData
import com.example.testforcoronaapp.model.statesModel.StatesData
import com.example.testforcoronaapp.repository.Repository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

class HomeFragmentViewModel(private val repository: Repository) : ViewModel() {

    private val gson = Gson()
    private var listOfJSONObjectDistrict = mutableListOf<JSONObject>()
    private var listOfDistrictObjects = mutableListOf<DistrictData>()
    private var listOfStatesObjects = mutableListOf<StatesData>()

    val statesDataLiveData: MutableLiveData<MutableList<StatesData>> = MutableLiveData()
    val districtDataLiveData: MutableLiveData<MutableList<DistrictData>> = MutableLiveData()

    fun getStatesDataViewModel() {
        viewModelScope.launch {
            val statesResponse = repository.getStatesDataRepo()
            convertStatesToJavaObject(statesResponse)
            statesDataLiveData.value = listOfStatesObjects
        }
    }

    fun getDistrictDataViewModel() {
        viewModelScope.launch {
            val districtResponse = repository.getDistrictDataRepo()
            convertDistrictsToJavaObject(districtResponse)
            districtDataLiveData.value = listOfDistrictObjects
        }
    }


    fun getServiceDataViewModel(){
        viewModelScope.launch {
            val districtResponse = repository.getDistrictDataRepo()
            convertDistrictsToJavaObject(districtResponse)
            districtDataLiveData.value = listOfDistrictObjects

            val statesResponse = repository.getStatesDataRepo()
            convertStatesToJavaObject(statesResponse)
            statesDataLiveData.value = listOfStatesObjects
        }
    }


    private fun convertDistrictsToJavaObject(districtResponse : Response<String>) {
        val jsonDataString = districtResponse.body()
        val json = JSONObject(jsonDataString!!)
        val data = json.getJSONObject("data")
        val allKeys = data.keys()
        allKeys.forEach { listOfJSONObjectDistrict.add(data.getJSONObject(it)) }

        for (listItem in listOfJSONObjectDistrict) {
            var jsonObjectString = listItem.toString()

            // Algorithm from https://stackoverflow.com/questions/57865562/java-replaceall-with-except-first-and-last-occurrence
            val sb = StringBuilder(jsonObjectString)
            val first: Int = jsonObjectString.indexOf("\"")
            val last: Int = jsonObjectString.lastIndexOf("\"")
            if (first != last) {
                var i = first + 1
                while (i < last) {
                    if (sb[i] == '\'') {
                        sb.insert(i, '\'')
                        i++
                    }
                    i++
                }
            }
            jsonObjectString = sb.toString()

            val districtData = gson.fromJson(jsonObjectString, DistrictData::class.java)
            listOfDistrictObjects.add(districtData)
        }
    }

    private fun convertStatesToJavaObject(statesResponse: Response<String>) {
        val jsonDataString = statesResponse.body()
        val json = JSONObject(jsonDataString!!)
        val data = json.getJSONObject("data")
        val allKeys = data.keys()
        allKeys.forEach { listOfJSONObjectDistrict.add(data.getJSONObject(it)) }

        for (listItem in listOfJSONObjectDistrict) {
            var jsonObjectString = listItem.toString()

            // Algorithm from https://stackoverflow.com/questions/57865562/java-replaceall-with-except-first-and-last-occurrence
            val sb = StringBuilder(jsonObjectString)
            val first: Int = jsonObjectString.indexOf("\"")
            val last: Int = jsonObjectString.lastIndexOf("\"")
            if (first != last) {
                var i = first + 1
                while (i < last) {
                    if (sb[i] == '\'') {
                        sb.insert(i, '\'')
                        i++
                    }
                    i++
                }
            }
            jsonObjectString = sb.toString()

            val statesData = gson.fromJson(jsonObjectString, StatesData::class.java)
            listOfStatesObjects.add(statesData)
        }
    }
}