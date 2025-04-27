package com.example.quikpik.presentation.feature.post.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.usecase.PostUseCases
import com.example.quikpik.domain.usecase.UserFeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userFeedUseCases: UserFeedUseCases,
    private val postUseCases: PostUseCases
) : ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchFeed()
    }

    fun clearState() {
        _state.value = HomeState()
    }

    fun likePost(postId: String) {
        viewModelScope.launch {

            try {
                // Optimistic update
                val post = _state.value.postData ?: return@launch
                val currentLikes =
                    post.find { it.id == postId }?.likes?.toMutableList() ?: mutableListOf()

                if (currentLikes.contains(postId)) {
                    currentLikes.remove(postId)
                } else {
                    currentLikes.add(postId)
                }

                // Update state immediately for UI
                _state.update { currentState ->
                    val updatedPosts = currentState.postData.map { post ->
                        if (post.id == postId) {
                            post.copy(likes = currentLikes)
                        } else {
                            post
                        }
                    }
                    currentState.copy(postData = updatedPosts)
                }

                // Make API call
                postUseCases.likePost(postId).collectLatest { res ->
                    when (res) {
                        is Resource.Success -> {
                    Log.d("TAG", "Likepost: ${res.data} ${res.message}")


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
            userFeedUseCases.getFeed().collectLatest { res ->
                when (res) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                postData = res.data as? List<DetailPostModel>
                                    ?: emptyList<DetailPostModel>(),
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