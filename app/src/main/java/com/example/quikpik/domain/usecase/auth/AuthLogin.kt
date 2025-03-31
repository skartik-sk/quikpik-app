package com.example.quikpik.domain.usecase.auth

import com.example.quikpik.domain.repo.AuthRepo
import javax.inject.Inject

class AuthLogin @Inject constructor(
    private val authRepository: AuthRepo
) {
      operator fun invoke(username:String, password:String)= authRepository.login(username,password)
}