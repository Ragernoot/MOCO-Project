package com.example.testforcoronaapp.repository

import com.example.testforcoronaapp.apis.RetrofitInstance
import com.example.testforcoronaapp.model.statesModel.StatesObject
import retrofit2.Response

class Repository {

    suspend fun getStatesDataRepo(): Response<String> {
        return RetrofitInstance.api.getStatesDataInterface()
    }

    suspend fun getDistrictDataRepo(): Response<String> {
        return RetrofitInstance.api.getDistrictDataInterface()
    }

}