package com.example.quikpik.domain.usecase.auth

import com.example.quikpik.domain.repo.AuthRepo
import javax.inject.Inject


class AuthLogout @Inject constructor(
    private val authRepository: AuthRepo
) {
    operator fun invoke()= authRepository.logout()
}