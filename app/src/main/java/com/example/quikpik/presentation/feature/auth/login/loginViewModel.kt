package com.example.quikpik.presentation.feature.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val authUseCases: AuthUseCases):ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state = _state



      fun login(username:String, password:String){
        viewModelScope.launch {
        authUseCases.authLogin(username, password).collect(){
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