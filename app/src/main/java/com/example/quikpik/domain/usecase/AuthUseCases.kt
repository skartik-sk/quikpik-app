package com.example.quikpik.domain.usecase

import com.example.quikpik.domain.usecase.auth.AuthForgot
import com.example.quikpik.domain.usecase.auth.AuthLogin
import com.example.quikpik.domain.usecase.auth.AuthLogout
import com.example.quikpik.domain.usecase.auth.AuthSignup

data class AuthUseCases(
    val authLogin: AuthLogin,
    val authSignup: AuthSignup,
    val authForgot: AuthForgot,
    val authLogout: AuthLogout

)
