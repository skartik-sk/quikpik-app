package com.example.quikpik.data.remort.Auth


data class SignupResponse(
    val message: String,
    val token: String,
    val user: String
)

data class SignupBody(
    val username: String,
    val email: String,
    val password: String

)