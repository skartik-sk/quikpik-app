package com.example.quikpik.domain.usecase.post

import com.example.quikpik.domain.repo.PostRepo
import javax.inject.Inject

class SavePost @Inject constructor(
    private val postRepository: PostRepo
) {
    operator fun invoke(postId: String) = postRepository.savePost(postId)
}