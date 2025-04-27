package com.example.quikpik.presentation.feature.post.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.PostUseCases
import com.example.quikpik.presentation.feature.profile.screens.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(val postUseCases: PostUseCases) : ViewModel() {

    private var _Likestate = MutableStateFlow(Unit)
    val Likestate = _Likestate.asStateFlow()

    private var _Bookmarkstate = MutableStateFlow(Unit)
    val Bookmarkstate = _Bookmarkstate.asStateFlow()



}