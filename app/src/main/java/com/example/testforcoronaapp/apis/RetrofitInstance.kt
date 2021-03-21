package com.example.testforcoronaapp.apis

import com.example.testforcoronaapp.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    val api : ICoronaDataApi by lazy {
        retrofit.create(ICoronaDataApi::class.java)
    }

}