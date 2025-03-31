package com.example.quikpik.data.repo

import android.content.Context
import android.net.Uri
import com.example.quikpik.common.Resource
import com.example.quikpik.data.entity.toDetailPostModel
import com.example.quikpik.data.entity.toPostModel
import com.example.quikpik.data.remort.PostApi
import com.example.quikpik.data.remort.others.TokenManager
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.PostModel
import com.example.quikpik.domain.repo.PostRepo
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

class PostRepoImpl @Inject constructor(
    private val postApi: PostApi,
    private val context: Context,
    private val tokenManager: TokenManager
) : PostRepo {

    override fun getAllPosts(): Flow<Resource<List<PostModel>>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.getAllPosts().map { it.toPostModel() }
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun getUserPosts(): Flow<Resource<List<PostModel>>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.getUserPost().map { it.toPostModel() }
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun getSavedPosts(): Flow<Resource<List<PostModel>>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.getSavedPosts().map { it.toPostModel() }
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun getPostById(postId: String): Flow<Resource<DetailPostModel>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.getPostById(postId).toDetailPostModel()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun uploadPost(imageUri: Uri, caption: String): Flow<Resource<PostModel>> = flow {
        try {
            emit(Resource.Loading())

            // Process image
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(imageUri)
            val fileType = contentResolver.getType(imageUri) ?: "image/*"
            val file = File.createTempFile("upload_", ".jpg")

            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            val requestFile = file.asRequestBody(fileType.toMediaTypeOrNull())
            val photoPart = MultipartBody.Part.createFormData("photo", file.name, requestFile)
            val captionBody = caption.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = postApi.uploadPost(photoPart, captionBody).toPostModel()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun updatePostCaption(postId: String, caption: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val jsonObject = JSONObject().put("caption", caption).toString()
            val captionBody = jsonObject.toRequestBody("application/json".toMediaTypeOrNull())
            val response = postApi.updatePostCaption(postId, captionBody)
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun deletePost(postId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.deletePost(postId)
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun likePost(postId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.likePost(postId)
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun savePost(postId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.savePost(postId)
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun addComment(postId: String, comment: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val jsonObject = JSONObject().put("comment", comment).toString()
            val commentBody = jsonObject.toRequestBody("application/json".toMediaTypeOrNull())
            val response = postApi.addComment(postId, commentBody)
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            emit(Resource.Error(parseErrorMessage(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun deleteComment(postId: String, commentId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = postApi.deleteComment(postId, commentId)
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
            val jsonObject = org.json.JSONObject(errorBody ?: "")
            jsonObject.optString("message", "Unknown error occurred")
        } catch (e: Exception) {
            errorBody ?: "Unknown error occurred"
        }
    }
}