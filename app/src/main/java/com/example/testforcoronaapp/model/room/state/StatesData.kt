package com.example.testforcoronaapp.model.room.state

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state")
data class StatesData (

        @PrimaryKey
        val id: Int,

        val name: String,
        val population: Int,
        val cases: Int,
        val deaths: Int,

        @ColumnInfo(name = "cases_per_week")
        val casesPerWeek: Int,

        @ColumnInfo(name = "deaths_per_week")
        val deathsPerWeek: Int,
        val recovered: Int,
        val abbreviation: String,

        @ColumnInfo(name = "week_incidence")
        val weekIncidence: Double,

        @ColumnInfo(name = "cases_per_100k")
        val casesPer100k: Double

)