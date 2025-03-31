package com.example.quikpik.domain.repo

import com.example.quikpik.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
     fun login(username: String, password: String):Flow<Resource<String>>
     fun signup(username: String, email: String, password: String):Flow<Resource<String>>
     fun forgotPass(email: String):Flow<Resource<String>>
     fun logout():Flow<Resource<String>>

}