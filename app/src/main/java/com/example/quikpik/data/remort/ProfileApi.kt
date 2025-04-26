package com.example.quikpik.data.remort

import com.example.quikpik.data.remort.Profile.DeleteProfileResponse
import com.example.quikpik.data.remort.Profile.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi{

    @GET("/user/profile")
    suspend fun getUserProfile(): ProfileResponse

    @Multipart
    @POST("/user/updateProfile")
    suspend fun updateUserProfile(@Part userphoto: MultipartBody.Part?,
                                  @Part("userData") userData: RequestBody
    ): ProfileResponse

    @DELETE("/user/deleteProfile")
    suspend fun deleteUserProfile(): DeleteProfileResponse
}