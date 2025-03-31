package com.example.quikpik.data.remort.Profile


import com.example.quikpik.data.entity.postEntity
import com.example.quikpik.data.entity.userEntity
import com.example.quikpik.domain.model.PostModel
import com.example.quikpik.domain.model.UserModel
import com.example.quikpik.domain.model.commentModel
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: List<String>,
    @SerializedName("following")
    val following: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("post")
    val post: List<String>,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("resetToken")
    val resetToken: String,
    @SerializedName("savedPosts")
    val savedPosts: List<String>,
    @SerializedName("username")
    val username: String,
    @SerializedName("__v")
    val v: Int
)

fun ProfileResponse.toUserModel(): UserModel {
    return UserModel(
        id=id,
        profileImage=profileImage,
        username=username,
        email=email,
        bio=bio,
        gender= gender,
        password= password,
        post= post,
        savedPosts= savedPosts,
        followers= followers,
        following= following,
        createdAt= createdAt,
        resetToken= resetToken




    )
}