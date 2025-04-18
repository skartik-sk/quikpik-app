package com.example.quikpik.data.remort

import com.example.quikpik.data.entity.DetailPostEntity
import com.example.quikpik.data.entity.DetailPostEntity1
import com.example.quikpik.data.remort.UserFeed.FollowResponse
import com.example.quikpik.data.remort.UserFeed.GetFollowersResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserFeedApi {
    @GET("/userFeed")
    suspend fun getPost(): List<DetailPostEntity1>


    @PUT("/userFeed/unfollow/{ids}")
    suspend fun unfollow(@Path("ids") userId: String): FollowResponse

    @PUT("/userFeed/follow/{ids}")
    suspend fun follow(@Path("ids") userId: String): FollowResponse

    @GET("/userFeed/getFollowers/")
    suspend fun getFollowers(): GetFollowersResponse



}