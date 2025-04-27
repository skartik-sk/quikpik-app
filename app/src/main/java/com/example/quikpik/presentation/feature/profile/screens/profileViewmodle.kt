package com.example.quikpik.presentation.feature.profile.screens

import com.example.quikpik.presentation.feature.post.screens.ExploreState


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val postUseCases: PostUseCases) : ViewModel() {

    private var _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        fetchFeed()
    }

    fun clearState() {
        _state.value = ProfileState()
    }

    fun likePost(postId: String) {
        viewModelScope.launch {

            try {
                // Optimistic update
                val post = _state.value.userData?.post ?: return@launch
                val currentLikes =
                    post.find { it.id == postId }?.likes?.toMutableList() ?: mutableListOf()

                if (currentLikes.contains(postId)) {
                    currentLikes.remove(postId)
                } else {
                    currentLikes.add(postId)
                }

                // Update state immediately for UI
                _state.update { currentState ->

                    val updatedPosts = currentState.userData?.post?.map { post ->
                        if (post.id == postId) {
                            post.copy(likes = currentLikes)
                        } else {
                            post
                        }
                    }
                    currentState.copy(userData = currentState.userData?.copy(post = updatedPosts?: emptyList()))
                }

                // Make API call
                postUseCases.likePost(postId).collectLatest { res ->
                    when (res) {
                        is Resource.Success -> {

                        }

                        is Resource.Error -> {
                            fetchFeed()
                            _state.update {
                                it.copy(
                                    error = res.message ?: "Failed to update like"
                                )
                            }
                        }

                        is Resource.Loading -> {

                        }
                    }
                }

            } catch (e: Exception) {
                // Revert on exception
                fetchFeed()
                _state.update {
                    it.copy(
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
            }


        }
    }
    fun fetchFeed() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            postUseCases.getUserPosts().collectLatest { res ->
                when (res) {
                    is Resource.Success -> {
                        if(res.data==null) {
                            _state.update {
                                it.copy(
                                    error = "No user data found",
                                    isLoading = false
                                )
                            }
                            return@collectLatest
                        }
                        _state.update {
                            it.copy(
                                userData = res.data,
                                isLoading = false
                            )

                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(error = res.message!!, isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

}