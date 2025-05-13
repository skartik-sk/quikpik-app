package com.example.quikpik.presentation.common

import android.R.attr.name
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.PostUseCases
import com.example.quikpik.domain.usecase.ProfileUseCases
import com.example.quikpik.domain.usecase.UserFeedUseCases
import com.example.quikpik.presentation.components.CustomToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.remove
import kotlin.text.contains

@HiltViewModel
class MeViewmodle @Inject constructor(private val profileUseCases: ProfileUseCases,private val postUseCases: PostUseCases, private  val userFeedUseCases: UserFeedUseCases) : ViewModel() {

    private var _state = MutableStateFlow(mestate())
    val state = _state.asStateFlow()

    private var _Likestate = MutableStateFlow(Unit)
    val Likestate = _Likestate.asStateFlow()

    private var _Bookmarkstate = MutableStateFlow(Unit)
    val Bookmarkstate = _Bookmarkstate.asStateFlow()


    private var hasInitialFetch = false
    fun refreshUserData() {
        hasInitialFetch=false
        fetchuser()
    }
    fun clearState() {
        _state.value = mestate()
    }
    fun updateUserData(imageUri: Uri, username:String, bio:String, gender:String, ) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                // Optimistic update
                val user = _state.value.userData ?: return@launch
                val updatedUser = user.copy(
                    profileImage = imageUri.encodedPath.toString(),
                    username = username,
                    bio = bio,

                )

                // Update state immediately for UI
                _state.update { currentState ->
                    currentState.copy(userData = updatedUser)
                }

                // Make API call
                profileUseCases.updateUserProfile( imageUri,username,bio,gender).collectLatest { res ->
                    when (res) {
                        is Resource.Success -> {
                            // Handle success if needed
                            _state.update { currentState ->
                                currentState.copy(userData = updatedUser)
                            }
                            fetchuser()

                        }

                        is Resource.Error -> {
                            fetchuser()
                        }

                        is Resource.Loading -> {
                            // Handle loading state if needed
                        }
                    }
                }

            } catch (e: Exception) {
                // Revert on exception
                fetchuser()
                _state.update { it.copy(
                    error = e.message ?: "An unexpected error occurred"
                )}
            }
        }
    }

    fun follow(userId: String) {
        viewModelScope.launch {
            try {
                // Optimistic update
                val user = _state.value.userData ?: return@launch
                user.following
                val currentFollowing = user.following.toMutableList()

                if (!currentFollowing.contains(userId)) {

                    currentFollowing.add(userId)
                }

                // Update state immediately for UI
                _state.update {
                    it.copy(
                        userData = user.copy(following = currentFollowing)
                    )
                }

                // Make API call
                userFeedUseCases.follow(userId).collectLatest { res ->
                    when (res) {
                        is Resource.Success -> {

                        }

                        is Resource.Error -> {

                            fetchuser()
                        }

                        is Resource.Loading -> {

                        }
                    }
                }

            } catch (e: Exception) {
                // Revert on exception
                fetchuser()
                _state.update { it.copy(
                    error = e.message ?: "An unexpected error occurred"
                )}
            }
        }
    }fun unfollow(userId: String) {
        viewModelScope.launch {
            try {
                // Optimistic update
                val user = _state.value.userData ?: return@launch
                user.following
                val currentFollowers = user.following.toMutableList()

                if (currentFollowers.contains(userId)) {
                    currentFollowers.remove(userId)
                } else {
                    currentFollowers.add(userId)
                }

                // Update state immediately for UI
                _state.update {
                    it.copy(
                        userData = user.copy(following = currentFollowers)
                    )
                }

                // Make API call
                userFeedUseCases.unfollow(userId).collectLatest { res ->
                    when (res) {
                        is Resource.Success -> {

                        }

                        is Resource.Error -> {

                            fetchuser()
                        }

                        is Resource.Loading -> {

                        }
                    }
                }

            } catch (e: Exception) {
                // Revert on exception
                fetchuser()
                _state.update { it.copy(
                    error = e.message ?: "An unexpected error occurred"
                )}
            }
        }
    }
    fun bookmarkPost(postId: String) {
        viewModelScope.launch {
            try {
                // Optimistic update
                val user = _state.value.userData ?: return@launch
                user.savedPosts
                val currentBookmarks = user.savedPosts.toMutableList()

                if (currentBookmarks.contains(postId)) {
                    currentBookmarks.remove(postId)
                } else {
                    currentBookmarks.add(postId)
                }

                // Update state immediately for UI
                _state.update {
                    it.copy(
                        userData = user.copy(savedPosts = currentBookmarks)
                    )
                }

                // Make API call
                postUseCases.savePost(postId).collectLatest { res ->
                    when (res) {
                        is Resource.Success -> {

                        }

                        is Resource.Error -> {

                            fetchuser()
                        }

                        is Resource.Loading -> {

                        }
                    }
                }

            } catch (e: Exception) {
                // Revert on exception
                fetchuser()
                _state.update { it.copy(
                    error = e.message ?: "An unexpected error occurred"
                )}
            }



        }
    }
    fun fetchuser(){
        if(!hasInitialFetch){

        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            profileUseCases.getUserProfile().collectLatest { res->
                when (res) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                userData = res.data,
                                isLoading = false
                            )

                        }
                        hasInitialFetch=true
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




}