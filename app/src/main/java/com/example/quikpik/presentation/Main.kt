package com.example.quikpik.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quikpik.common.Screen
import com.example.quikpik.presentation.common.MeViewmodle
import com.example.quikpik.presentation.feature.auth.signup.Signup
import com.example.quikpik.presentation.feature.message.MessageScreen
import com.example.quikpik.presentation.feature.navigation.BottomNavigationBar
import com.example.quikpik.presentation.feature.post.components.DetailsPostScreen
import com.example.quikpik.presentation.feature.post.screens.ExploreScreen
import com.example.quikpik.presentation.feature.post.screens.HomeScreen
import com.example.quikpik.presentation.feature.post.screens.PostScreen
import com.example.quikpik.presentation.feature.profile.screens.EditProfileScreen
import com.example.quikpik.presentation.feature.profile.screens.ProfileScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier,
               meViewmodel: MeViewmodle
               ) {
    val navController: NavHostController = rememberNavController()

    // Main screen content goes here
    // You can use the BottomNavigationBar and other components as needed
    // For example:
    // BottomNavigationBar(bottomNavController = navController)

    Scaffold(
                bottomBar = { BottomNavigationBar(navController) }
    ) {
        // Add padding to the content based on the bottom bar height
        val padding = it
        LaunchedEffect(Unit) {
            meViewmodel.fetchuser()
        }


        NavHost(navController = navController, startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController ,
                    meViewmodel = meViewmodel)
            }
            composable(Screen.Messages.route) {
                MessageScreen(navController= navController)
            }
            composable(Screen.Explore.route) {
                ExploreScreen(navController= navController,
                    ExploreViewModel = hiltViewModel(),
                    meViewmodel = meViewmodel
                )
            }
            composable (Screen.Post.route){
                PostScreen(
                    navController = navController,
                )
            }
            composable(Screen.Profile.route) { ProfileScreen(
                navController=navController,
                meViewmodel = meViewmodel
            ) }
            composable ( Screen.PostDetails.route + "/{postId}") {
                val postId = it.arguments?.getString("postId")
                if (postId != null) {
                    DetailsPostScreen(
                        navController = navController,
                        meViewModel = meViewmodel,
                    )
                }

            }
            composable(Screen.EditScreen.route) {
                EditProfileScreen(navController = navController,meViewModel = meViewmodel,)
            }



        }

    }



}