package com.example.quikpik.data.remort

import com.example.quikpik.data.entity.DetailPostEntity
import com.example.quikpik.data.remort.UserFeed.FollowResponse
import com.example.quikpik.data.remort.UserFeed.GetFollowersResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserFeedApi {
    @GET("/")
    suspend fun getPost(): DetailPostEntity


    @PUT("/unfollow/{ids}")
    suspend fun unfollow(@Path("ids") userId: String): FollowResponse

    @PUT("/follow/{ids}")
    suspend fun follow(@Path("ids") userId: String): FollowResponse

    @GET("/getFollowers/")
    suspend fun getFollowers(): GetFollowersResponse



}