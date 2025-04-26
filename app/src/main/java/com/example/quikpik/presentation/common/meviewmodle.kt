package com.example.quikpik.presentation.common

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.usecase.ProfileUseCases
import com.example.quikpik.presentation.feature.post.screens.ExploreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeViewmodle @Inject constructor(private val profileUseCases: ProfileUseCases) : ViewModel() {

    private var _state = MutableStateFlow(mestate())
    val state = _state.asStateFlow()
    private var hasInitialFetch = false
    fun refreshUserData() {
        hasInitialFetch=false;
        fetchuser()
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
                        hasInitialFetch=true;
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