package com.example.quikpik.presentation.feature.post.screens

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
fun HomeScreen(modifier: Modifier = Modifier,
               navController: NavHostController,
                HomeViewModel: HomeViewModel = hiltViewModel(),
               meViewmodel: MeViewmodle
)  {
    val homeState = HomeViewModel.state.collectAsState().value
    val meState = meViewmodel.state.collectAsState().value
    if (homeState.isLoading) {
        return CustomLoading()
    }
    if (homeState.postData.isNotEmpty() && meState.userData!=null  ) {        val reversedList = homeState.postData.reversed()

        return PostList(reversedList,userdata = meState.userData!!,
            onLikeClick = { postId -> HomeViewModel.likePost(postId) },
            onBookmarkClick = { postId -> meViewmodel.bookmarkPost(postId) },

                    onPostClick = {  navController.navigate(Screen.PostDetails.route+ "/${it}")}
        )
    }
    if (homeState.error.isNotEmpty()) {
        return CustomToast(homeState.error,true) { }
    }


}