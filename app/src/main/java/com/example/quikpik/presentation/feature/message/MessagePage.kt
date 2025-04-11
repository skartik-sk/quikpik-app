package com.example.quikpik.presentation.feature.message

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MessageScreen(modifier: Modifier = Modifier,
                  navController: NavHostController)  {
    Column(){

        Text("This is message screen")
    }
}