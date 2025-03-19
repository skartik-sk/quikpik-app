package com.example.quikpik.data.entity


data class userEntity(
    val id: String,
    val profileImage: String,
    val username: String,
    val email: String,
    val bio: String,
    val gender: String,
    val password: String,
val post: List<PostReference>,
val savedPosts: List<PostReference>,
val followers: List<UserReference>,
val following: List<UserReference>,
    val createdAt: String,
    val resetToken: String
)

sealed class PostReference {
    data class PostId(val id: String) : PostReference()
    data class PostEntity(val post: postEntity) : PostReference()
}

sealed class  UserReference {
    data class UserId(val id: String) : UserReference()
    data class UserEntity(val user: userEntity) : UserReference()
}
