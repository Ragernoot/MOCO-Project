package com.example.testforcoronaapp.apis

import com.example.testforcoronaapp.model.statesModel.StatesObject
import retrofit2.Response
import retrofit2.http.GET

interface ICoronaDataApi {

    @GET("states")
    suspend fun getStatesDataInterface(): Response<String>

    @GET("districts")
    suspend fun getDistrictDataInterface(): Response<String>


}