package com.example.quikpik.domain.usecase.post

import android.net.Uri
import com.example.quikpik.domain.repo.PostRepo
import javax.inject.Inject

class UploadPost @Inject constructor(
    private val postRepository: PostRepo
) {
    operator fun invoke(imageUri: Uri, caption: String) =
        postRepository.uploadPost(imageUri, caption)
}