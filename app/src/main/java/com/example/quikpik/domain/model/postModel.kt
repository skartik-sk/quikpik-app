package com.example.quikpik.domain.model

data class PostModel(
    val id: String,
    val image: String,
    val comments: List<commentModel>,
    val caption: String,
    val likes: List<String>,
    val createdBy: String,
    val createdAt: String
)

data class commentModel(
    val comment: String,
    val commenter: String
)
data class commentModel1(
    val comment: String,
    val commenter: createdBy
)




