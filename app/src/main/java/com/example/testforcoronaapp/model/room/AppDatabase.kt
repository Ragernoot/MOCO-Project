package com.example.testforcoronaapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testforcoronaapp.model.room.currentDistrict.CurrentDistrict
import com.example.testforcoronaapp.model.room.currentDistrict.CurrentDistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.lastDistrict.LastDistrict
import com.example.testforcoronaapp.model.room.lastDistrict.LastDistrictDAO
import com.example.testforcoronaapp.model.room.state.StateDAO
import com.example.testforcoronaapp.model.room.state.StatesData


@Database(entities = [DistrictData::class, StatesData::class, LastDistrict::class, CurrentDistrict::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stateDAO(): StateDAO
    abstract fun districtDAO() : DistrictDAO
    abstract fun lastDistrictDAO() : LastDistrictDAO
    abstract fun currentDistrictDAO() : CurrentDistrictDAO

}