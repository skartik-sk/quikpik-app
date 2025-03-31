package com.example.quikpik.domain.usecase

import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.MiniProfile
import com.example.quikpik.domain.usecase.feed.*
import kotlinx.coroutines.flow.Flow

data class UserFeedUseCases(
    val getFeed: GetFeed,


val getFollowes:GetFollowers,
val follow:Follow,
val unfollow:UnFollow
)