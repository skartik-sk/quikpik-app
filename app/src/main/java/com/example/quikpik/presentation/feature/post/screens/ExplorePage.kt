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
import com.example.quikpik.presentation.components.CustomLoading
import com.example.quikpik.presentation.components.CustomToast
import com.example.quikpik.presentation.feature.post.components.PostList

@Composable
fun ExploreScreen(modifier: Modifier = Modifier,
               navController: NavHostController
                , ExploreViewModel: ExploreViewModel = hiltViewModel()

)  {
    val exploreState = ExploreViewModel.state.collectAsState().value;
    Log.d("ExploreScreen", "ExploreScreen: $exploreState")
    if (exploreState.isLoading) {
        return CustomLoading()
    }
    if (exploreState.postData.isNotEmpty()) {
        return PostList(exploreState.postData) { }
    }
    if (exploreState.error.isNotEmpty()) {
        return CustomToast(exploreState.error,true) { }
    }
}