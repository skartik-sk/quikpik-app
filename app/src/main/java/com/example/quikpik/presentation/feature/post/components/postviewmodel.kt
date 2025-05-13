package com.example.quikpik.presentation.feature.post.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun clearState() {
        _state.value = PostState()
    }


    fun uploadPost(imageUri: Uri, comment: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                postUseCases.uploadPost(imageUri, comment).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isSuccess = true,
                                error = ""
                            )
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Error uploading post"
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error uploading post: ${e.message}")
            }

        }
    }

}

data class PostState(
    val isLoading: Boolean = false,
    val error: String = "",
    var isSuccess: Boolean = false
)

