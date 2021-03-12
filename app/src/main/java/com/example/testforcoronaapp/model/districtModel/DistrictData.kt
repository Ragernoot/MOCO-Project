package com.example.testforcoronaapp.model.districtModel

import com.google.gson.annotations.SerializedName

class DistrictData {

    val ags: String = ""
    val name: String = ""
    val county: String = ""
    val state: String = ""
    val population: Int = 0
    val cases: Int = 0
    val deaths: Int = 0
    val casesPerWeek: Int = 0
    val deathsPerWeek: Int = 0
    val stateAbbreviation: String = ""
    val recovered: Int = 0
    val weekIncidence: Double = 0.0
    val casesPer100k: Double = 0.0

    @SerializedName("delta")
    val deltaDistrict: DistrictDeltaData = DistrictDeltaData()

}