package com.example.testforcoronaapp.model.room

import androidx.room.*
import com.example.testforcoronaapp.model.room.district.DistrictDAO
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StateDAO
import com.example.testforcoronaapp.model.room.state.StatesData


@Database(entities = [DistrictData::class, StatesData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stateDAO(): StateDAO
    abstract fun districtDAO() : DistrictDAO

}