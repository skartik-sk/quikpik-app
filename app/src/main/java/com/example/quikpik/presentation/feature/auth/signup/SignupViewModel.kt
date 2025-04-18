package com.example.quikpik.presentation.feature.auth.signup

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.usecase.AuthUseCases
import com.example.quikpik.presentation.feature.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(val authUseCases: AuthUseCases):ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    fun clearState() {
        _state.value = SignupState()
    }
      fun Signup(username:String, password:String, email: String){
          viewModelScope.launch {
              _state.update {
                  it.copy(isLoading = true)
              }
              authUseCases.authSignup(username,email, password).collectLatest{
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



}