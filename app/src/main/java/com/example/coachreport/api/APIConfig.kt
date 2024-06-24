package com.example.coachreport.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {
    const val baseurl = "https://drc.d2l.my.id/"

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() : APIService {
        return getRetrofit().create(APIService::class.java)
    }
}