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
import com.example.quikpik.presentation.feature.auth.login.LoginViewModel
import com.example.quikpik.presentation.feature.post.components.PostList
import kotlin.math.log


@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               navController: NavHostController,
                HomeViewModel: HomeViewModel = hiltViewModel()
)  {
    val homeState = HomeViewModel.state.collectAsState().value;
        Log.d("HomeScreen", "HomeScreen: $homeState")
    if (homeState.isLoading) {
        return CustomLoading()
    }
    if (homeState.postData.isNotEmpty()) {
        return PostList(homeState.postData) { }
    }
    if (homeState.error.isNotEmpty()) {
        return CustomToast(homeState.error,true) { }
    }


}