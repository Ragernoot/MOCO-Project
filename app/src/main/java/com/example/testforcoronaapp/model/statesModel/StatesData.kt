package com.example.testforcoronaapp.model.statesModel

import com.google.gson.annotations.SerializedName

class StatesData {

        val id = 0
        val name: String = ""
        val population: Int = 0
        val cases: Int = 0
        val deaths: Int = 0
        val casesPerWeek: Int = 0
        val deathsPerWeek: Int = 0
        val recovered: Int = 0
        val abbreviation: String = ""
        val weekIncidence: Double = 0.0
        val casesPer100k: Double = 0.0

        @SerializedName("delta")
        val deltaStates: StatesDeltaData = StatesDeltaData()

}