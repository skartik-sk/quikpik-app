package com.example.quikpik.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quikpik.common.Screen
import com.example.quikpik.presentation.feature.auth.login.Login
import com.example.quikpik.presentation.feature.auth.signup.Signup
import com.example.quikpik.presentation.feature.message.MessageScreen
import com.example.quikpik.presentation.feature.navigation.BottomNavigationBar
import com.example.quikpik.presentation.feature.post.Screens.ExploreScreen
import com.example.quikpik.presentation.feature.post.Screens.HomeScreen
import com.example.quikpik.presentation.feature.profile.view.ProfileScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier,
               ) {
    val navController: NavHostController = rememberNavController()
    // Main screen content goes here
    // You can use the BottomNavigationBar and other components as needed
    // For example:
    // BottomNavigationBar(bottomNavController = navController)

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) {
        val padding = it
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Messages.route) {
                MessageScreen(navController= navController)
            }
            composable(Screen.Explore.route) {
                ExploreScreen(navController= navController)
            }
            composable(Screen.Profile.route) { ProfileScreen(navController=navController) }



        }

    }



}