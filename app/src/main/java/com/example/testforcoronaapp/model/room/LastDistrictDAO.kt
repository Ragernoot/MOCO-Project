package com.example.testforcoronaapp.model.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.testforcoronaapp.model.room.district.DistrictData

interface LastDistrictDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg district : DistrictData)


    @Delete
    fun delete(vararg district: LastDistrict)

}