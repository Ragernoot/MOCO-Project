package com.example.coronarisiko.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.coronarisiko.model.room.AppDatabase
import com.example.coronarisiko.model.room.currentDistrict.CurrentDistrict
import com.example.coronarisiko.model.room.currentDistrict.CurrentDistrictDAO
import com.example.coronarisiko.model.room.lastDistrict.LastDistrict
import com.example.coronarisiko.model.room.lastDistrict.LastDistrictDAO
import com.example.coronarisiko.utils.Constants
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
            currentDistrict.value = currentDistrictDAO.getCurrentDistrictSuspend()
            lastDistrict.value = lastDistrictDAO.getLastDistrictSuspend()
        }
    }
}