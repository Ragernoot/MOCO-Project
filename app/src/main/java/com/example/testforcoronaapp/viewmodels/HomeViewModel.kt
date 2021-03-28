package com.example.testforcoronaapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StateDAO
import com.example.testforcoronaapp.model.room.state.StatesData
import com.example.testforcoronaapp.utils.Constants.Companion.DATABASE_NAME
import kotlinx.coroutines.launch

class HomeViewModel(application : Application) : AndroidViewModel(application) {

    val stateDataLiveData: MutableLiveData<Array<StatesData>> = MutableLiveData()
    val districtDataLiveData: MutableLiveData<Array<DistrictData>> = MutableLiveData()

    private var stateDAO: StateDAO
    private var districtDAO: DistrictDAO

    init {
        val context = application.applicationContext
        val database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).build()

        stateDAO = database.stateDAO()
        districtDAO = database.districtDAO()
    }

    fun getDataFromRoom() {
        viewModelScope.launch {
            stateDataLiveData.value = stateDAO.funGetAllStates()
            districtDataLiveData.value = districtDAO.funGetAllDistricts()
            Log.e("INIT ", "INIT IST DURCH")
            Log.e("INIT ", stateDAO.funGetAllStates().toString())
        }
    }
}