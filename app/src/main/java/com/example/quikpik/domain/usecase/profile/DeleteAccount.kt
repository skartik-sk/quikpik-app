package com.example.quikpik.domain.usecase.profile

import com.example.quikpik.domain.repo.ProfileRepo
import javax.inject.Inject

class DeleteAccount @Inject constructor(
    private val profileRepository: ProfileRepo
) {
    operator fun invoke() = profileRepository.deleteAccount()
}