package com.example.quikpik.data.entity

import com.example.quikpik.domain.model.DetailUserModel
import com.example.quikpik.domain.model.UserModel


data class userEntity(
    val _id: String,
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

data class detailUserEntity(
    val _id: String,
    val profileImage: String,
    val username: String,
    val email: String,
    val bio: String,
    val gender: String,
    val password: String,
    val post: List<DetailPostEntity1>,
    val savedPosts: List<DetailPostEntity1>,
    val followers: List<String>,
    val following: List<String>,
    val createdAt: String,
    val resetToken: String
)
fun detailUserEntity.toDetialUserModel(): DetailUserModel {
    return DetailUserModel(
        id = _id,
        profileImage = profileImage,
        username = username,
        email = email,
        bio = bio,
        gender = gender,
        password = password,
        post = post.toDetailPostModelList(),
        savedPosts = savedPosts.toDetailPostModelList(),
        followers = followers,
        following = following,
        createdAt = createdAt,
        resetToken = resetToken
    )
}


fun userEntity.toUserModel():UserModel{
    return UserModel(
        id=_id,
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


