package com.example.quikpik.domain.repo

import android.net.Uri
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.PostModel
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.DetailUserModel
import kotlinx.coroutines.flow.Flow

interface PostRepo {
    fun getAllPosts(): Flow<Resource<List<DetailPostModel>>>
    fun getUserPosts():  Flow<Resource<DetailUserModel>>
    fun getSavedPosts(): Flow<Resource<List<PostModel>>>
    fun getPostById(postId: String): Flow<Resource<DetailPostModel>>
    fun uploadPost(imageUri: Uri, caption: String): Flow<Resource<PostModel>>
    fun updatePostCaption(postId: String, caption: String): Flow<Resource<String>>
    fun deletePost(postId: String): Flow<Resource<String>>
    fun likePost(postId: String): Flow<Resource<String>>
    fun savePost(postId: String): Flow<Resource<String>>
    fun addComment(postId: String, comment: String): Flow<Resource<String>>
    fun deleteComment(postId: String, commentId: String): Flow<Resource<String>>
}