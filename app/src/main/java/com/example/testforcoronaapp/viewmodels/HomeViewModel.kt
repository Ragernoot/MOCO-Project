package com.example.testforcoronaapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StatesData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class HomeViewModel(database: AppDatabase) : ViewModel() {
    var stateDataLiveData: LiveData<Array<StatesData>>
    var districtDataLiveData: LiveData<Array<DistrictData>>

    val statesDAO = database.stateDAO()
    val districtDAO = database.districtDAO()

    init{
        stateDataLiveData = statesDAO.allStates
        districtDataLiveData = districtDAO.allDistricts
    }
}