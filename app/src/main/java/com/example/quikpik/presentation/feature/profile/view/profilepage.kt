package com.example.quikpik.presentation.feature.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(modifier: Modifier = Modifier,
                  navController: NavHostController)  {
    Column(){

        Text("This is Profile screen")
    }
}