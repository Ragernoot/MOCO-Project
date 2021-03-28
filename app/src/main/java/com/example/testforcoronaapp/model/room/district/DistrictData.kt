package com.example.testforcoronaapp.model.room.district

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "district")
data class DistrictData (

        @PrimaryKey
        val ags: String,
        val name: String,
        val county: String,
        val state: String,
        val population: Int,
        val cases: Int,
        val deaths: Int,

        @ColumnInfo(name = "cases_per_week")
        val casesPerWeek: Int,

        @ColumnInfo(name = "deaths_per_week")
        val deathsPerWeek: Int,

        @ColumnInfo(name = "state_abbreviation")
        val stateAbbreviation: String?,
        val recovered: Int,

        @ColumnInfo(name = "week_incidence")
        val weekIncidence: Double,

        @ColumnInfo(name = "cases_per_100k")
        val casesPer100k: Double

)