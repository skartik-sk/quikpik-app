package com.example.quikpik.presentation.feature.post.screens

import com.example.quikpik.domain.model.DetailPostModel


data class ExploreState(
    val postData: List<DetailPostModel> = emptyList<DetailPostModel>(),
    val isLoading: Boolean = false,
    val error: String = ""
)