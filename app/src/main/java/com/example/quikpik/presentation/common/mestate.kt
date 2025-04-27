package com.example.quikpik.presentation.common

import com.example.quikpik.domain.model.UserModel

data class mestate (
    val userData : UserModel?=null,
    val isLoading: Boolean = false,
    val error: String = ""

)
