package com.example.quikpik.common

sealed class screen(val route :String) {
    object Home : screen("home")
    object Login : screen("login")
    object Signup : screen("signup")
    object ForgotPassword : screen("forgot_password")

}