package com.example.testforcoronaapp.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Brauchen wir das ?

@Entity
data class LastDistrict (

    @PrimaryKey
    val id: Int,
    val name: String,
    val population: Int,
    val cases: Int,
    val deaths: Int,
    val casesPerWeek: Int,
    val deathsPerWeek: Int,
    val recovered: Int,
    val abbreviation: String,
    val weekIncidence: Double,
    val casesPer100k: Double

)