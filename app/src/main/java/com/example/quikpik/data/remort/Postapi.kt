package com.example.quikpik.data.remort

import com.example.quikpik.data.entity.DetailPostEntity
import com.example.quikpik.data.entity.postEntity
import com.example.quikpik.data.remort.Auth.ForgotBody
import com.example.quikpik.data.remort.Auth.ForgotRes
import com.example.quikpik.data.remort.Auth.LoginBody
import com.example.quikpik.data.remort.Auth.LoginResponse
import com.example.quikpik.data.remort.Auth.Logoutres
import com.example.quikpik.data.remort.Auth.SignupBody
import com.example.quikpik.data.remort.Post.PostMessageResponse
import com.example.quikpik.data.remort.UserFeed.FollowResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PostApi {


    @PUT("/{id}/caption")
    suspend fun updatePostCaption(
        @Path("id") postId: String,
        @Body caption: RequestBody
    ): PostMessageResponse

    @DELETE("/{id}")
    suspend fun deletePost(@Path("id") postId: String): PostMessageResponse

    @GET("/")
    suspend fun getAllPosts(): List<postEntity>

    @GET("/{id}")
    suspend fun getPostById(@Path("id") postId: String): DetailPostEntity

    @PUT("/{id}/comment")
    suspend fun addComment(
        @Path("id") postId: String,
        @Body comment: RequestBody
    ): PostMessageResponse

    @PUT("/{id}/delcomment/{commentId}")
    suspend fun deleteComment(
        @Path("id") postId: String,
        @Path("commentId") commentId: String
    ): PostMessageResponse

    @PUT("/{id}/like")
    suspend fun likePost(@Path("id") postId: String): PostMessageResponse

    @POST("{ids}/saveAPost")
    suspend fun savePost(@Path("ids") postId: String): PostMessageResponse


    @POST("/getUserPosts")
    suspend fun getUserPost(
    ): List<postEntity>

    @POST("/getSavedPosts")
    suspend fun getSavedPosts(
    ): List<postEntity>





    @Multipart
    @POST("/")
    suspend fun uploadPost(
        @Part photo: MultipartBody.Part,
        @Part("caption") caption: RequestBody
    ): postEntity







}