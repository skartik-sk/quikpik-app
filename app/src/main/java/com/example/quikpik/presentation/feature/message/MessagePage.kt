package com.example.quikpik.presentation.feature.message

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MessageScreen(modifier: Modifier = Modifier,
                  navController: NavHostController)  {
    Column(){

        Text("This Feature is Coming Soon...", fontSize = 18.sp, fontFamily = FontFamily.Monospace)
    }
}