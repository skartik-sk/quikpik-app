package com.example.quikpik.data.repo

import com.example.quikpik.common.Resource
import com.example.quikpik.data.remort.Auth.ForgotBody
import com.example.quikpik.data.remort.Auth.LoginBody
import com.example.quikpik.data.remort.Auth.SignupBody
import com.example.quikpik.data.remort.AuthApi
import com.example.quikpik.data.remort.others.TokenManager
import com.example.quikpik.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    val authApi: AuthApi,
    private val tokenManager: TokenManager


) : AuthRepo {
    override fun login(username: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val data = LoginBody(username, password)
            val response = authApi.login(data)
            response.token.let { tokenManager.saveToken(token = it) }
            emit(Resource.Success(data=response.message))

        } catch (e: HttpException) {
            // Handle HTTP errors (like 404)
            val errorBody = e.response()?.errorBody()?.string()
            // Try to parse the error message from JSON if available
            val errorMessage = try {
                val jsonObject = org.json.JSONObject(errorBody ?: "")
                jsonObject.optString("message", "Unknown error occurred")
            } catch (e: Exception) {
                errorBody ?: "Unknown error occurred"
            }
            emit(Resource.Error(errorMessage))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.message ?: "An unexpected error occurred",
                    data = e.localizedMessage
                )
            )

        }
    }
    override fun signup(username: String, email: String, password: String): Flow<Resource<String>> =
        flow {
            try {
                emit(Resource.Loading())
                val data = SignupBody(username, email, password)
                val response = authApi.signup(data)
                response.token.let { tokenManager.saveToken(token = it) }
                emit(Resource.Success(response.message))

            } catch (e: HttpException) {
                // Handle HTTP errors (like 404)
                val errorBody = e.response()?.errorBody()?.string()
                // Try to parse the error message from JSON if available
                val errorMessage = try {
                    val jsonObject = org.json.JSONObject(errorBody ?: "")
                    jsonObject.optString("message", "Unknown error occurred")
                } catch (e: Exception) {
                    errorBody ?: "Unknown error occurred"
                }
                emit(Resource.Error(errorMessage))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        e.message ?: "An unexpected error occurred",
                        data = e.localizedMessage
                    )
                )

            }
        }

    override fun forgotPass(email: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val data = ForgotBody(email)
            val response = authApi.forgotPassword(data)
            emit(Resource.Success(response.message))

        } catch (e: HttpException) {
            // Handle HTTP errors (like 404)
            val errorBody = e.response()?.errorBody()?.string()
            // Try to parse the error message from JSON if available
            val errorMessage = try {
                val jsonObject = org.json.JSONObject(errorBody ?: "")
                jsonObject.optString("message", "Unknown error occurred")
            } catch (e: Exception) {
                errorBody ?: "Unknown error occurred"
            }
            emit(Resource.Error(errorMessage))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.message ?: "An unexpected error occurred",
                    data = e.localizedMessage
                )
            )

        }
    }

    override fun logout(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val cookie = "access_token=${tokenManager.getToken()}"
            val result = authApi.logout(cookie)
            tokenManager.deleteToken()
            emit(Resource.Success(result.message))

        }catch (e: HttpException) {
            // Handle HTTP errors (like 404)
            val errorBody = e.response()?.errorBody()?.string()
            // Try to parse the error message from JSON if available
            val errorMessage = try {
                val jsonObject = org.json.JSONObject(errorBody ?: "")
                jsonObject.optString("message", "Unknown error occurred")
            } catch (e: Exception) {
                errorBody ?: "Unknown error occurred"
            }
            emit(Resource.Error(errorMessage))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.message ?: "An unexpected error occurred",
                    data = e.localizedMessage
                )
            )

        }

    }

}