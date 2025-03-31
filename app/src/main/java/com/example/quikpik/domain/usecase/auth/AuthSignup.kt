package com.example.quikpik.domain.usecase.auth

import com.example.quikpik.domain.repo.AuthRepo
import javax.inject.Inject

class AuthSignup  @Inject constructor(
    private val authRepository: AuthRepo
) {
    suspend operator fun invoke(username:String,email:String, password:String)= authRepository.signup(username,email,password)
}
