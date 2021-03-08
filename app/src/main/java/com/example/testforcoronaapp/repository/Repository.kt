package com.example.testforcoronaapp.repository

import android.util.Log
import com.example.testforcoronaapp.apis.RetrofitInstance
import com.example.testforcoronaapp.model.districtModel.DistrictObject
import com.example.testforcoronaapp.model.statesModel.StatesObject
import com.example.testforcoronaapp.utils.Constants
import retrofit2.Response

class Repository {

    suspend fun getStatesDataRepo(): Response<StatesObject> {
        Log.e("WAS SOLL DER SCHEIß", Constants.BASE_URL)
        return RetrofitInstance.api.getStatesDataInterface()
    }

    suspend fun getDistrictDataRepo(): Response<DistrictObject> {
        return RetrofitInstance.api.getDistrictDataInterface()
    }

}