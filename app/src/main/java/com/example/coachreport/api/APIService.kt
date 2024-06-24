package com.example.coachreport.api

import com.example.coachreport.api.response.LoginResponse
import com.example.coachreport.api.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface APIService {

    @FormUrlEncoded
    @POST("api/login")
    fun AuthLogin(
        @Field("noHp") phone: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/register")
    fun AuthRegist(
        body: RegisterResponse)
//    fun AuthRegist(
//        @Field("nama") nama: String,
//        @Field("noHp") noHp: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//        @Field("password_confirmation") password_confirmation: String,
//    ) : Call<RegisterResponse>
}

