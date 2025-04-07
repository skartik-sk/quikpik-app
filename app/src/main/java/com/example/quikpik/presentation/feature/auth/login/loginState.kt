package com.example.quikpik.presentation.feature.auth.login


data class LoginState(
    val message: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)