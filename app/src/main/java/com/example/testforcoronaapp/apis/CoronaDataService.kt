package com.example.testforcoronaapp.apis

import com.example.testforcoronaapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.Request

class CoronaDataService {
    private val client = OkHttpClient()

    fun getStates(): String? {
        val request: Request = Request.Builder()
            .url(BASE_URL + "states")
            .build()

        client.newCall(request).execute().use { response ->
            return response.body?.string()
        }
    }
}