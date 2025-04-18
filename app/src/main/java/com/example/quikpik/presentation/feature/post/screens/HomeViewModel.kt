package com.example.quikpik.presentation.feature.post.screens

import android.R.attr.password
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.usecase.AuthUseCases
import com.example.quikpik.domain.usecase.UserFeedUseCases
import com.example.quikpik.presentation.feature.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val userFeedUseCases: UserFeedUseCases):ViewModel() {

private var _state = MutableStateFlow(HomeState())
val state = _state.asStateFlow()

    init {
        fetchFeed()
    }
fun clearState() {
    _state.value = HomeState()
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
                            postData = res.data as? List<DetailPostModel> ?: emptyList<DetailPostModel>()
,
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