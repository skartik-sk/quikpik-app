package com.example.quikpik.domain.usecase

import com.example.quikpik.domain.usecase.profile.*

data class ProfileUseCases(
    val getUserProfile: GetUserProfile,
    val updateUserProfile: UpdateUserProfile,
    val deleteAccount: DeleteAccount
)