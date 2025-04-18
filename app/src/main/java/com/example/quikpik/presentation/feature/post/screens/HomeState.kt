package com.example.quikpik.presentation.feature.post.screens

import com.example.quikpik.domain.model.DetailPostModel


data class HomeState(
    val postData: List<DetailPostModel> = emptyList<DetailPostModel>(),
    val isLoading: Boolean = false,
    val error: String = ""
)