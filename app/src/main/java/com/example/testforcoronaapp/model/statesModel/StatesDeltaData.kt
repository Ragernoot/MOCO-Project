package com.example.testforcoronaapp.model.statesModel

import com.google.gson.annotations.SerializedName

class StatesDeltaData {

    @SerializedName("cases")
    val changedCases: Int = 0
    @SerializedName("deaths")
    val changedDeaths: Int = 0
    @SerializedName("recovered")
    val changedRecovered: Int = 0
}