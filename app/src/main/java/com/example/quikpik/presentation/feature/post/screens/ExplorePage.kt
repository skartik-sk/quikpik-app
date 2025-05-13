package com.example.quikpik.presentation.feature.post.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quikpik.common.Screen
import com.example.quikpik.presentation.common.MeViewmodle
import com.example.quikpik.presentation.components.CustomLoading
import com.example.quikpik.presentation.components.CustomToast
import com.example.quikpik.presentation.feature.post.components.PostList

@Composable
fun ExploreScreen(modifier: Modifier = Modifier,
               navController: NavHostController
                , ExploreViewModel: ExploreViewModel = hiltViewModel()
                  ,
                  meViewmodel: MeViewmodle

)  {
    val exploreState = ExploreViewModel.state.collectAsState().value
    val meState = meViewmodel.state.collectAsState().value
    Log.d("ExploreScreen", "ExploreScreen: $exploreState")
    if (exploreState.isLoading) {
        return CustomLoading()
    }
    if (exploreState.postData.isNotEmpty()&& meState.userData!=null  ) {
        //reverse the list
        val reversedList = exploreState.postData.reversed()
        return PostList(reversedList,userdata = meState.userData!!,
            onLikeClick = { postId -> ExploreViewModel.likePost(postId,meState.userData.id) },
            onBookmarkClick = { postId -> meViewmodel.bookmarkPost(postId) },
            onFollowClick = { userId -> meViewmodel.follow(userId) },
            onUnFollowClick = { userId -> meViewmodel.unfollow(userId) },

            onPostClick = {
                navController.navigate(Screen.PostDetails.route+ "/${it}")
            }
        )
    }

    if (exploreState.error.isNotEmpty()) {
        return CustomToast(exploreState.error,true) { }
    }
}