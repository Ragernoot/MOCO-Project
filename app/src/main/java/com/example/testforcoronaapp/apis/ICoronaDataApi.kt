package com.example.testforcoronaapp.apis

import com.example.testforcoronaapp.model.districtModel.DistrictObject
import com.example.testforcoronaapp.model.districtModel.OtherDistrictObject
import com.example.testforcoronaapp.model.statesModel.StatesObject
import retrofit2.Response
import retrofit2.http.GET

interface ICoronaDataApi {

    @GET("states")
    suspend fun getStatesDataInterface(): Response<StatesObject>

    @GET("districts")
    suspend fun getDistrictDataInterface(): Response<DistrictObject>

    @GET("districts")
    suspend fun getDistrictDataInterface2(): Response<OtherDistrictObject>

    @GET("districts")
    suspend fun getDistrictDataInterfaceString(): Response<String>


}