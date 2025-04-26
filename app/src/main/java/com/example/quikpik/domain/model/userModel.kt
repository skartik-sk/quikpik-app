package com.example.quikpik.domain.model

import com.example.quikpik.data.entity.DetailPostEntity1


data class UserModel(
    val id: String,
    val profileImage: String,
    val username: String,
    val email: String,
    val bio: String,
    val gender: String,
    val password: String,
val post: List<String>,
val savedPosts: List<String>,
val followers: List<String>,
val following: List<String>,
    val createdAt: String,
    val resetToken: String
)

data class MiniProfile(
    val id:String,
    val profileImage: String,
)

data class DetailUserModel(
    val id: String,
    val profileImage: String,
    val username: String,
    val email: String,
    val bio: String,
    val gender: String,
    val password: String,
    val post: List<DetailPostModel>,
    val savedPosts: List<DetailPostModel>,
    val followers: List<String>,
    val following: List<String>,
    val createdAt: String,
    val resetToken: String

)

