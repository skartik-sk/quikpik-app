package com.example.quikpik.domain.usecase.profile

import android.net.Uri
import com.example.quikpik.domain.repo.ProfileRepo
import javax.inject.Inject

class UpdateUserProfile @Inject constructor(
    private val profileRepository: ProfileRepo
) {
    operator fun invoke(
        imageUri: Uri? = null,
        username: String? = null,
        bio: String? = null,
        gender: String? = null
    ) = profileRepository.updateUserProfile(imageUri, username, bio, gender)
}