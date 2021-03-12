package com.example.testforcoronaapp.model.districtModel

import com.google.gson.annotations.SerializedName

class DistrictDeltaData {
    @SerializedName("cases")
    val changedCases: Int = 0
    @SerializedName("deaths")
    val changedDeaths: Int = 0
    @SerializedName("recovered")
    val changedRecovered: Int = 0
}