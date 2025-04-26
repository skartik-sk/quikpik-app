package com.example.quikpik.presentation.feature.profile.screens

import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.DetailUserModel

data class ProfileState(
    val userData: DetailUserModel? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)