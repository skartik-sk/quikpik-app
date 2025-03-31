package com.example.quikpik.data.remort.UserFeed

data class GetFollowersResponse (
    val followers:List<Follower>? = emptyList()

)

data class Follower(
    val id:String,
    val photo:String
)

