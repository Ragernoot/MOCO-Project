package com.example.testforcoronaapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StatesData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(database: AppDatabase) : ViewModel() {

    val stateDataLiveData: MutableLiveData<Array<StatesData>> = MutableLiveData()
    val districtDataLiveData: MutableLiveData<Array<DistrictData>> = MutableLiveData()

    val statesDAO = database.stateDAO()
    val districtDAO = database.districtDAO()

    fun getDataFromRoom(){
        viewModelScope.launch {
            delay(1000)
            stateDataLiveData.value = statesDAO.loadAllStates()
            districtDataLiveData.value = districtDAO.loadAllDistricts()
        }
    }
}