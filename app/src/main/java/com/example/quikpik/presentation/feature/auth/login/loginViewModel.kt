package com.example.quikpik.presentation.feature.auth.login

import android.R.id.message
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val authUseCases: AuthUseCases):ViewModel() {
    private var _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun clearState() {
        _state.value = LoginState()
    }
      fun login(username:String, password:String){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
        authUseCases.authLogin(username, password).collectLatest{
            res->
            when(res){
                is Resource.Success -> {
                    _state.update {
                        it.copy(message = res.data!!,isLoading = false)
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy( error = res.message!!, isLoading = false)
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }}
    }

    fun logout(){
        viewModelScope.launch {
            authUseCases.authLogout().collect(){
                when(it){
                    is Resource.Success -> {
                        _state.value = LoginState( message = it.data?:"Somthing went wrong")
                    }
                    is Resource.Error -> {
                        _state.value = it.message?.let { it1 -> LoginState(message =it.message , error = it1,) }!!
                    }
                    is Resource.Loading -> {
                        _state.value = LoginState(isLoading = true)
                    }
                }
            }
        }
    }

}