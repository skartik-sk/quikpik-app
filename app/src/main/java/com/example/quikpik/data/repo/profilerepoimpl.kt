
package com.example.quikpik.data.repo

import android.content.Context
import android.net.Uri
import com.example.quikpik.common.Resource
import com.example.quikpik.data.entity.toUserModel
import com.example.quikpik.data.remort.Profile.UpdateProfileResquest
import com.example.quikpik.data.remort.Profile.toUserModel
import com.example.quikpik.data.remort.ProfileApi
import com.example.quikpik.data.remort.others.TokenManager
import com.example.quikpik.domain.model.UserModel
import com.example.quikpik.domain.repo.ProfileRepo
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val context: Context,
    private val tokenManager: TokenManager
) : ProfileRepo {

    override fun getUserProfile(): Flow<Resource<UserModel>> = flow {
        try {
            emit(Resource.Loading())
            val response = profileApi.getUserProfile().toUserModel()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun updateUserProfile(
        imageUri: Uri?,
        username: String?,
        bio: String?,
        gender: String?
    ): Flow<Resource<UserModel>> = flow {
        try {
            emit(Resource.Loading())

            // Create update profile data
            val updateData = UpdateProfileResquest(
                username = username,
                bio = bio,
                gender = gender
            )

            // Convert to JSON and then RequestBody
            val gson = Gson()
            val jsonString = gson.toJson(updateData)
            val userDataBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

            // Process image if provided
            val imagePart = imageUri?.let { uri ->
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                val fileType = contentResolver.getType(uri) ?: "image/*"
                val file = File.createTempFile("profile_", ".jpg")

                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val requestFile = file.asRequestBody(fileType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData("userphoto", file.name, requestFile)
            }

            val response = profileApi.updateUserProfile(imagePart, userDataBody).toUserModel()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }


    override fun deleteAccount(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = profileApi.deleteUserProfile()
            tokenManager.deleteToken()
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    private fun parseErrorMessage(e: HttpException): String {
        val errorBody = e.response()?.errorBody()?.string()
        return try {
            val jsonObject = JSONObject(errorBody ?: "")
            jsonObject.optString("message", "Unknown error occurred")
        } catch (e: Exception) {
            errorBody ?: "Unknown error occurred"
        }
    }
}