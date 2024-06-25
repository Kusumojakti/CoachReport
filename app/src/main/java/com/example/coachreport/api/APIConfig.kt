package com.example.coachreport.api

import android.content.Context
import com.example.coachreport.utils.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {
    const val baseurl = "https://drc.d2l.my.id/"

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(APIInterceptor(context))
            .build()
    }
    fun getRetrofit(context: Context) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .client(getOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(context: Context) : APIService {
        return getRetrofit(context).create(APIService::class.java)
    }
}