package com.example.quikpik.domain.usecase.post

import com.example.quikpik.domain.repo.PostRepo
import javax.inject.Inject

class DeleteComment @Inject constructor(
    private val postRepository: PostRepo
) {
    operator fun invoke(postId: String, commentId: String) =
        postRepository.deleteComment(postId, commentId)
}