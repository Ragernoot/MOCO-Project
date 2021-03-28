package com.example.coronarisiko.apis

import com.example.coronarisiko.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    val api : ICoronaDataApi by lazy {
        retrofit.create(ICoronaDataApi::class.java)
    }

}