package com.example.testforcoronaapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.districtModel.DistrictObject
import com.example.testforcoronaapp.model.statesModel.StatesObject
import com.example.testforcoronaapp.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val statesDataLiveData: MutableLiveData<StatesObject> = MutableLiveData()
    val districtDataLiveData: MutableLiveData<DistrictObject> = MutableLiveData()

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