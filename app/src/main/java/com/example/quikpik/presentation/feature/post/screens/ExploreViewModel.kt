package com.example.quikpik.presentation.feature.post.screens

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
class ExploreViewModel @Inject constructor(val postUseCases: PostUseCases) : ViewModel() {

    private var _state = MutableStateFlow(ExploreState())
    val state = _state.asStateFlow()

    init {
        fetchFeed()
    }

    fun clearState() {
        _state.value = ExploreState()
    }
    fun likePost(postId: String,userid: String) {
        viewModelScope.launch {

            try {
                // Optimistic update
                val post = _state.value.postData ?: return@launch
                val currentLikes =
                    post.find { it.id == postId }?.likes?.toMutableList() ?: mutableListOf()

                if (currentLikes.contains(userid)) {
                    currentLikes.remove(userid)
                } else {
                    currentLikes.add(userid)
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
            postUseCases.getAllPosts().collectLatest { res ->
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