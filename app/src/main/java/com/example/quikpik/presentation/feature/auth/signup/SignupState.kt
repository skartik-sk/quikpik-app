package com.example.quikpik.presentation.feature.auth.signup


data class SignupState(
    val message: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)