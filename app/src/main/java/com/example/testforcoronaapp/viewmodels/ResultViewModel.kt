package com.example.testforcoronaapp.viewmodels

import android.app.Application
import androidx.core.content.contentValuesOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.currentDistrict.CurrentDistrict
import com.example.testforcoronaapp.model.room.currentDistrict.CurrentDistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.lastDistrict.LastDistrict
import com.example.testforcoronaapp.model.room.lastDistrict.LastDistrictDAO
import com.example.testforcoronaapp.model.room.state.StateDAO
import com.example.testforcoronaapp.model.room.state.StatesData
import com.example.testforcoronaapp.utils.Constants
import kotlinx.coroutines.launch

class ResultViewModel(application : Application) : AndroidViewModel(application) {

    val currentDistrict: MutableLiveData<CurrentDistrict> = MutableLiveData()
    val lastDistrict: MutableLiveData<LastDistrict> = MutableLiveData()

    private var lastDistrictDAO : LastDistrictDAO
    private var currentDistrictDAO : CurrentDistrictDAO

    init {
        val context = application.applicationContext
        val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, Constants.DATABASE_NAME
        ).build()

        lastDistrictDAO = database.lastDistrictDAO()
        currentDistrictDAO = database.currentDistrictDAO()

    }

    fun getData() {
        viewModelScope.launch {
            currentDistrict.value = currentDistrictDAO.getLastCurrentDistrictSuspend()
            lastDistrict.value = lastDistrictDAO.getLastDistrictSuspend()
        }
    }
}