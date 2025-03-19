package com.example.quikpik.data.remort.Auth


data class LoginResponse(
    val message: String,
    val token: String,
    val user: String
)

data class LoginBody(
    val username: String,
    val password: String
)