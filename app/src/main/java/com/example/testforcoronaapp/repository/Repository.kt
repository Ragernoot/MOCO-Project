package com.example.testforcoronaapp.repository

import android.util.Log
import com.example.testforcoronaapp.apis.RetrofitInstance
import com.example.testforcoronaapp.model.districtModel.DistrictObject
import com.example.testforcoronaapp.model.districtModel.OtherDistrictObject
import com.example.testforcoronaapp.model.statesModel.StatesObject
import com.example.testforcoronaapp.utils.Constants
import retrofit2.Response

class Repository {

    suspend fun getStatesDataRepo(): Response<StatesObject> {
        return RetrofitInstance.api.getStatesDataInterface()
    }

    suspend fun getDistrictDataRepo(): Response<DistrictObject> {
        return RetrofitInstance.api.getDistrictDataInterface()
    }

    suspend fun getDistrictDataRepo2(): Response<OtherDistrictObject> {
        return RetrofitInstance.api.getDistrictDataInterface2()
    }

    suspend fun getDistrictDataRepoString(): Response<String> {
        return RetrofitInstance.apiString.getDistrictDataInterfaceString()
    }

}