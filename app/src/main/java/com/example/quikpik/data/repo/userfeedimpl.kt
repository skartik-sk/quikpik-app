package com.example.quikpik.data.repo
import android.util.Log
import com.example.quikpik.common.Resource
import com.example.quikpik.data.entity.toDetailPostModelList
import com.example.quikpik.data.remort.AuthApi
import com.example.quikpik.data.remort.UserFeedApi
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.MiniProfile
import com.example.quikpik.domain.repo.UserFeedRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class Userfeedimpl@Inject  constructor(
    private val userFeedApi: UserFeedApi
): UserFeedRepo {
 override fun getPost(): Flow<Resource<List<DetailPostModel>>> = flow {
     try {
         emit(Resource.Loading())
         val response = userFeedApi.getPost()
         Log.d("Userfeedimpl", "getPost: $response")
         emit(Resource.Success(response.toDetailPostModelList()))
     } catch (e: HttpException) {
         emit(Resource.Error(parseErrorMessage(e)))
     } catch (e: Exception) {
         emit(Resource.Error(e.message ?: "An unexpected error occurred"))
     }
 }

 override fun getFollowers(): Flow<Resource<List<MiniProfile>>> = flow {
     try {
         emit(Resource.Loading())
         val response = userFeedApi.getFollowers().followers?.map {
             MiniProfile(
                 id = it.id,
                 profileImage = it.photo,
             )
         }
         if(response.isNullOrEmpty()) {
             emit(Resource.Error("No followers found"))
             return@flow
         }
         emit(Resource.Success(response))
     } catch (e: HttpException) {
         emit(Resource.Error(parseErrorMessage(e)))
     } catch (e: Exception) {
         emit(Resource.Error(e.message ?: "An unexpected error occurred"))
     }
 }

 override fun follow(userId: String): Flow<Resource<String>> = flow {
     try {
         emit(Resource.Loading())
         val response = userFeedApi.follow(userId)
         emit(Resource.Success(response.message))
     } catch (e: HttpException) {
         emit(Resource.Error(parseErrorMessage(e)))
     } catch (e: Exception) {
         emit(Resource.Error(e.message ?: "An unexpected error occurred"))
     }
 }

 override fun unfollow(userId: String): Flow<Resource<String>> = flow {
     try {
         emit(Resource.Loading())
         val response = userFeedApi.unfollow(userId)
         emit(Resource.Success(response.message))
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