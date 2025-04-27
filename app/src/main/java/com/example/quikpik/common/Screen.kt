package com.example.quikpik.common

sealed class Screen(val route :String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Explore : Screen("explore")
    object Post : Screen("post")
    object Messages : Screen("messages")
    object Main: Screen("main")
    object EditScreen: Screen("edit_screen")
    object PostDetails: Screen("post_details")


}