package com.example.quikpik.data.entity

data class postEntity(
    val id: String,
    val image: String,
    val comments: List<commentEntity>,
    val caption: String,
    val likes: List<likeEntity>,
    val createdBy: String,
    val createdAt: String
)

data class commentEntity(
    val comment: String,
    val commenter: String
)

data class likeEntity(
    val like: String
)


