package com.example.quikpik.data.remort

import com.example.quikpik.data.remort.Auth.ForgotBody
import com.example.quikpik.data.remort.Auth.ForgotRes
import com.example.quikpik.data.remort.Auth.LoginBody
import com.example.quikpik.data.remort.Auth.LoginResponse
import com.example.quikpik.data.remort.Auth.SignupBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body loginBody: LoginBody): Call<LoginResponse>

    @POST("signup")
    @Headers("Content-Type: application/json")
    suspend fun signup(@Body signupBody: SignupBody): Call<LoginResponse>

    @GET("logout")
    suspend fun logout(): String

    @POST("forgotPassword")
    suspend fun forgotPassword(@Body forgotBody: ForgotBody): Call<ForgotRes>

    @POST("resetPassword/:id/:token")
    suspend fun resetPassword(): String





}