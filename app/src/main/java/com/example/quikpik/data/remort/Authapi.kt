package com.example.quikpik.data.remort

import com.example.quikpik.data.remort.Auth.ForgotBody
import com.example.quikpik.data.remort.Auth.ForgotRes
import com.example.quikpik.data.remort.Auth.LoginBody
import com.example.quikpik.data.remort.Auth.LoginResponse
import com.example.quikpik.data.remort.Auth.Logoutres
import com.example.quikpik.data.remort.Auth.SignupBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body loginBody: LoginBody): LoginResponse

    @POST("signup")
    @Headers("Content-Type: application/json")
    suspend fun signup(@Body signupBody: SignupBody): LoginResponse

    @GET("logout")
    @Headers("Content-Type: application/json")
    suspend fun logout(@Header("Cookie") cookie:String): Logoutres

    @POST("forgotPassword")
    @Headers("Content-Type: application/json")
    suspend fun forgotPassword(@Body forgotBody: ForgotBody): ForgotRes

    @POST("resetPassword/:id/:token")
    suspend fun resetPassword(): String





}