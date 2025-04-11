package com.example.quikpik.presentation.feature.auth.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.AuthUseCases
import com.example.quikpik.presentation.feature.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(val authUseCases: AuthUseCases):ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state = _state
      fun Signup(username:String, password:String, email: String){
        viewModelScope.launch {
        authUseCases.authSignup(username, email , password,).collect(){
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



}