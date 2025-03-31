package com.example.quikpik.data.entity

import com.example.quikpik.domain.model.PostModel
import com.example.quikpik.domain.model.commentModel

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
// Extension function to convert postEntity to PostModel
fun postEntity.toPostModel(): PostModel {
    return PostModel(
        id = id,
        image = image,
        comments = comments.map { commentModel(it.comment, it.commenter) },
        caption = caption,
        likes = likes.map { it.like },
        createdBy = createdBy,
        createdAt = createdAt
    )
}

// Extension function to convert PostModel to postEntity



