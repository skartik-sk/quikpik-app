package com.example.quikpik.data.entity

import com.example.quikpik.domain.model.DetailPostModel

data class DetailPostEntity1(
    val _id: String,
    val image: String,
    val comments: List<detailcommentEntity1>,
    val caption: String,
    val likes: List<String>,
    val createdBy: createdBy,
    val createdAt: String
)
data class detailcommentEntity1(
    val comment: String,
    val commenter: String
)

data class DetailPostEntity(
    val _id: String,
    val image: String,
    val comments: List<detailcommentEntity>,
    val caption: String,
    val likes: List<String>,
    val createdBy: createdBy,
    val createdAt: String
)
data class detailcommentEntity(
    val comment: String,
    val commenter: createdBy
)

data class createdBy(
    val _id: String,
    val profileImage: String,
    val username: String,
    val bio: String,
    val followers: List<String>,
    val following: List<String>
)


fun DetailPostEntity.toDetailPostModel(): DetailPostModel {
    return DetailPostModel(
        id = _id,
        image = image,
        comments = comments.map {
            com.example.quikpik.domain.model.commentModel(
                comment = it.comment,
                commenter = com.example.quikpik.domain.model.createdBy(
                    id = it.commenter._id,
                    profileImage = it.commenter.profileImage,
                    username = it.commenter.username,
                    bio = it.commenter.bio,
                    followers = it.commenter.followers,
                    following = it.commenter.following
                ).toString()
            )
        },
        caption = caption,
        likes = likes.map { it },
        createdBy = com.example.quikpik.domain.model.createdBy(
            id = createdBy._id,
            profileImage = createdBy.profileImage,
            username = createdBy.username,
            bio = createdBy.bio,
            followers = createdBy.followers,
            following = createdBy.following
        ),
        createdAt = createdAt
    )
}


fun List<DetailPostEntity1>.toDetailPostModelList(): List<DetailPostModel> {
    return this.map { detailPostEntity ->
        DetailPostModel(
            id = detailPostEntity._id,
            image = detailPostEntity.image,
            comments = detailPostEntity.comments.map {
                com.example.quikpik.domain.model.commentModel(
                    comment = it.comment,
                    commenter = it.commenter
                )
            },
            caption = detailPostEntity.caption,
            likes = detailPostEntity.likes.map { it },
            createdBy = com.example.quikpik.domain.model.createdBy(
                id = detailPostEntity.createdBy._id,
                profileImage = detailPostEntity.createdBy.profileImage,
                username = detailPostEntity.createdBy.username,
                bio = detailPostEntity.createdBy.bio,
                followers = detailPostEntity.createdBy.followers,
                following = detailPostEntity.createdBy.following
            ),
            createdAt = detailPostEntity.createdAt
        )
    }
}
