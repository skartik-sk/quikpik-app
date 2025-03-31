package com.example.quikpik.data.entity

import com.example.quikpik.domain.model.DetailPostModel

data class DetailPostEntity(
    val id: String,
    val image: String,
    val comments: List<detailcommentEntity>,
    val caption: String,
    val likes: List<likeEntity>,
    val createdBy: createdBy,
    val createdAt: String
)
data class detailcommentEntity(
    val comment: String,
    val commenter: createdBy
)

data class createdBy(
    val id: String,
    val profileImage: String,
    val username: String,
    val bio: String,
    val followers: List<String>,
    val following: List<String>
)

fun DetailPostEntity.toDetailPostModel(): DetailPostModel {
    return DetailPostModel(
        id = id,
        image = image,
        comments = comments.map {
            com.example.quikpik.domain.model.commentModel(
                comment = it.comment,
                commenter = com.example.quikpik.domain.model.createdBy(
                    id = it.commenter.id,
                    profileImage = it.commenter.profileImage,
                    username = it.commenter.username,
                    bio = it.commenter.bio,
                    followers = it.commenter.followers,
                    following = it.commenter.following
                ).toString()
            )
        },
        caption = caption,
        likes = likes.map { it.like },
        createdBy = com.example.quikpik.domain.model.createdBy(
            id = createdBy.id,
            profileImage = createdBy.profileImage,
            username = createdBy.username,
            bio = createdBy.bio,
            followers = createdBy.followers,
            following = createdBy.following
        ),
        createdAt = createdAt
    )
}
