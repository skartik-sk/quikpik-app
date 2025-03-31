package com.example.quikpik.presentation.screens.login


data class LoginState(
    val message: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)