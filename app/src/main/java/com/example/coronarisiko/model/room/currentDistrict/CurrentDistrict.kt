package com.example.coronarisiko.model.room.currentDistrict

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_district")
data class CurrentDistrict (

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