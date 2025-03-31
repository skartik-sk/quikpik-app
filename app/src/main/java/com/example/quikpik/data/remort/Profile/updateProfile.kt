package com.example.quikpik.data.remort.Profile
import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
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

data class UpdateProfileResquest(
val username: String? = null,
val bio: String? = null,
val gender: String? = null,
    val email:String? = null

)