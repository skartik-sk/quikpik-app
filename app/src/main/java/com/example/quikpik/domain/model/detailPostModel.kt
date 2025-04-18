package com.example.quikpik.domain.model

import com.example.quikpik.data.entity.likeEntity

data class DetailPostModel(
    val id: String,
    val image: String,
    val comments: List<commentModel>? = emptyList(),
    val caption: String,
    val likes: List<String>,
    val createdBy: createdBy,
    val createdAt: String
)


data class createdBy(
    val id: String,
    val profileImage: String,
    val username: String,
    val bio: String,
    val followers: List<String>,
    val following: List<String>
)