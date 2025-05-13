package com.example.quikpik.presentation.feature.post.components

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.DetailPostModel1
import com.example.quikpik.domain.model.commentModel
import com.example.quikpik.domain.model.commentModel1
import com.example.quikpik.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PostDetailsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            getPostById(postId)
        }
    }

    fun getPostById(postId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            postUseCases.getPostById(postId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                post = result.data,
                                comments = result.data?.comments ?: emptyList(),
                                isLoading = false,
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Error loading post"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }



    fun likePost(postId: String,userid:String) {
        viewModelScope.launch {
            try {
                val currentState = _state.value
                val post = currentState.post ?: return@launch

                // Update UI optimistically
                val updatedLikes = post.likes.toMutableList()

                if (updatedLikes.contains(userid)) {
                    updatedLikes.remove(userid)
                } else {
                    updatedLikes.add(userid)
                }

                _state.update {
                    it.copy(
                        post = post.copy(likes = updatedLikes)
                    )
                }

                // Make API call
                postUseCases.likePost(postId).collectLatest { result ->
                    if (result is Resource.Error) {
                        // Revert on error
                        getPostById(postId)
                        _state.update {
                            it.copy(error = result.message ?: "Failed to update like")
                        }
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(error = e.message ?: "An unexpected error occurred")
                }
            }
        }
    }

    fun addComment(postId: String, content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isAddingComment = true) }

            postUseCases.addComment(postId, content).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        getPostById(postId)
                        _state.update { it.copy(isAddingComment = false, newCommentText = "") }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isAddingComment = false,
                                error = result.message ?: "Failed to add comment"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isAddingComment = true) }
                    }
                }
            }
        }
    }

    fun updateNewCommentText(text: String) {
        _state.update { it.copy(newCommentText = text) }
    }
}

data class PostDetailsState(
    val post: DetailPostModel1? = null,
    val comments: List<commentModel1> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isAddingComment: Boolean = false,
    val newCommentText: String = ""
)