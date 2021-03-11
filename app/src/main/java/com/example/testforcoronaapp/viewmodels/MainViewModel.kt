package com.example.testforcoronaapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.statesModel.StatesObject
import com.example.testforcoronaapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val statesDataLiveData: MutableLiveData<Response<String>> = MutableLiveData()
    val districtDataLiveData: MutableLiveData<Response<String>> = MutableLiveData()

    fun getStatesDataViewModel() {
        viewModelScope.launch {
            val statesResponse = repository.getStatesDataRepo()
            statesDataLiveData.value = statesResponse
        }
    }

    fun getDistrictDataViewModel() {
        viewModelScope.launch {
            val districtResponse = repository.getDistrictDataRepo()
            districtDataLiveData.value = districtResponse
        }
    }

}