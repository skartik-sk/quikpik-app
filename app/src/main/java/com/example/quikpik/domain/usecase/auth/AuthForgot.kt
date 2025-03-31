package com.example.quikpik.domain.usecase.auth

import com.example.quikpik.domain.repo.AuthRepo
import javax.inject.Inject

class AuthForgot  @Inject constructor(
        private val authRepository: AuthRepo
    ) {
        suspend operator fun invoke(email:String)= authRepository.forgotPass(email)
    }
