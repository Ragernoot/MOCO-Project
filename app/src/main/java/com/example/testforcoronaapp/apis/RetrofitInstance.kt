package com.example.testforcoronaapp.apis

import com.example.testforcoronaapp.utils.Constants.Companion.BASE_URL
import com.example.testforcoronaapp.utils.Constants.Companion.OTHER_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(OTHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofitString by lazy {
        Retrofit.Builder()
                .baseUrl(OTHER_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }


    val api : ICoronaDataApi by lazy {
           retrofit.create(ICoronaDataApi::class.java)
    }

    val apiString : ICoronaDataApi by lazy {
        retrofitString.create(ICoronaDataApi::class.java)
    }

}