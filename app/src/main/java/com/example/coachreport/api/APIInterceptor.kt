package com.example.coachreport.api

import android.content.Context
import com.example.coachreport.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class APIInterceptor (private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SessionManager.getToken(context)
        val requestBuilder = if (!token.isNullOrBlank()){
            chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $token")
                .build()
            } else {
                chain.request()
            }
        return chain.proceed(requestBuilder)
        }
    }