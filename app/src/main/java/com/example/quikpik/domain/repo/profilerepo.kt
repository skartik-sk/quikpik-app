
package com.example.quikpik.domain.repo

import android.net.Uri
import com.example.quikpik.common.Resource
import com.example.quikpik.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    fun getUserProfile(): Flow<Resource<UserModel>>
    fun updateUserProfile(
        imageUri: Uri?,
        username: String?,
        bio: String?,
        gender: String?
    ): Flow<Resource<UserModel>>

    fun deleteAccount(): Flow<Resource<String>>
}