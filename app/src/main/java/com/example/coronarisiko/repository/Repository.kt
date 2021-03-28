package com.example.coronarisiko.repository

import com.example.coronarisiko.apis.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getStatesDataRepo(): Response<String> {
        return RetrofitInstance.api.getStatesDataInterface()
    }

    suspend fun getDistrictDataRepo(): Response<String> {
        return RetrofitInstance.api.getDistrictDataInterface()
    }

    fun getStatesDataRepoTest(): Response<String>{
        return RetrofitInstance.api.getStatesDataInterfaceTest()
    }
}