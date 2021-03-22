package com.example.testforcoronaapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.districtModel.DistrictData
import com.example.testforcoronaapp.model.statesModel.StatesData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.utils.SomeAlgorithms
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class DataServiceViewModel(private val repository: Repository) : ViewModel() {

    private val gson = Gson()
    private var listOfJSONObjectDistrict = mutableListOf<JSONObject>()
    private var listOfJSONObjectStates = mutableListOf<JSONObject>()

    private var listOfDistrictObjects = mutableListOf<DistrictData>()
    private var listOfStatesObjects = mutableListOf<StatesData>()

    val statesDataLiveData: MutableLiveData<MutableList<StatesData>> = MutableLiveData()
    val districtDataLiveData: MutableLiveData<MutableList<DistrictData>> = MutableLiveData()


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