package com.example.quikpik.data.remort

import com.example.quikpik.data.entity.DetailPostEntity
import com.example.quikpik.data.entity.DetailPostEntity1
import com.example.quikpik.data.entity.detailUserEntity
import com.example.quikpik.data.entity.postEntity
import com.example.quikpik.data.remort.Post.PostMessageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PostApi {


    @PUT("post/{id}/caption")
    suspend fun updatePostCaption(
        @Path("id") postId: String,
        @Body caption: RequestBody
    ): PostMessageResponse

    @DELETE("post/{id}")
    suspend fun deletePost(@Path("id") postId: String): PostMessageResponse

    @GET("post/")
    suspend fun getAllPosts(): List<DetailPostEntity1>

    @GET("post/{id}")
    suspend fun getPostById(@Path("id") postId: String): DetailPostEntity

    @PUT("post/{id}/comment")
    suspend fun addComment(
        @Path("id") postId: String,
        @Body comment: RequestBody
    ): PostMessageResponse

    @PUT("post/{id}/delcomment/{commentId}")
    suspend fun deleteComment(
        @Path("id") postId: String,
        @Path("commentId") commentId: String
    ): PostMessageResponse

    @PUT("post/{id}/like")
    suspend fun likePost(@Path("id") postId: String): PostMessageResponse

    @POST("post/{ids}/saveAPost")
    suspend fun savePost(@Path("ids") postId: String): PostMessageResponse


    @POST("post/getUserPosts")
    suspend fun getUserPost(
    ): detailUserEntity

    @POST("post/getSavedPosts")
    suspend fun getSavedPosts(
    ): List<postEntity>





    @Multipart
    @POST("post/")
    suspend fun uploadPost(
        @Part photo: MultipartBody.Part,
        @Part("caption") caption: RequestBody
    ): postEntity







}