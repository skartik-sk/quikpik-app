package com.example.quikpik.domain.usecase

import com.example.quikpik.domain.usecase.post.*

data class PostUseCases(
    val getAllPosts: GetAllPosts,
    val getUserPosts: GetUserPosts,
    val getSavedPosts: GetSavedPosts,
    val getPostById: GetPostById,
    val uploadPost: UploadPost,
    val updatePostCaption: UpdatePostCaption,
    val deletePost: DeletePost,
    val likePost: LikePost,
    val savePost: SavePost,
    val addComment: AddComment,
    val deleteComment: DeleteComment
)