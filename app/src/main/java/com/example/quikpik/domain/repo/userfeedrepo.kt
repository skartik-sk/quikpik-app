package com.example.quikpik.domain.repo

import com.example.quikpik.common.Resource
import com.example.quikpik.data.remort.UserFeed.FollowResponse
import com.example.quikpik.data.remort.UserFeed.GetFollowersResponse
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.MiniProfile
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserFeedRepo {




fun getPost(): Flow<Resource<DetailPostModel>>
    fun getFollowers(): Flow<Resource<List<MiniProfile>>>
    fun follow(userId: String): Flow<Resource<String>>
    fun unfollow(userId: String): Flow<Resource<String>>



}