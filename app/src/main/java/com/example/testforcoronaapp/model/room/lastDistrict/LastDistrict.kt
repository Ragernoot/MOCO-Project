package com.example.testforcoronaapp.model.room.lastDistrict

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testforcoronaapp.model.room.district.DistrictData


@Entity(tableName = "last_district")
data class LastDistrict(

        @PrimaryKey
        val ags: String,
        val name: String,
        val county: String,
        val state: String,
        val population: Int,
        val cases: Int,
        val deaths: Int,
        val casesPerWeek: Int,
        val deathsPerWeek: Int,
        val stateAbbreviation: String?,
        val recovered: Int,
        val weekIncidence: Double,
        val casesPer100k: Double

)