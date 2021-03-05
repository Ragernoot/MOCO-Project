package com.example.testforcoronaapp.repository

import com.example.testforcoronaapp.apis.RetrofitInstance
import com.example.testforcoronaapp.model.districtModel.DistrictObject
import com.example.testforcoronaapp.model.statesModel.StatesObject

class Repository {

    suspend fun getStatesDataRepo(): StatesObject {
        return RetrofitInstance.api.getStatesDataInterface()
    }

    suspend fun getDistrictDataRepo(): DistrictObject {
        return RetrofitInstance.api.getDistrictDataInterface()
    }

}