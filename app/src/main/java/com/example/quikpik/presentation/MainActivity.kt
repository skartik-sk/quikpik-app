package com.example.quikpik.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quikpik.common.Screen
import com.example.quikpik.data.remort.others.TokenManager
import com.example.quikpik.presentation.common.MeViewmodle
import com.example.quikpik.presentation.feature.auth.login.Login
import com.example.quikpik.presentation.feature.auth.signup.Signup
import com.example.quikpik.presentation.feature.navigation.BottomNavigationBar
import com.example.quikpik.presentation.ui.theme.QuikPikTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            QuikPikTheme {
Scaffold(){padding->
    val paddingvalue = padding



                    val navController = rememberNavController()
                val meViewModel: MeViewmodle = hiltViewModel()



                NavHost(navController = navController, startDestination = if(tokenManager.getToken()
                        ?.isNotEmpty() == true
                )Screen.Main.route else Screen.Login.route  ) {
                        composable(Screen.Login.route) {
                            Login(navController = navController)
                        }
                        composable(Screen.Signup.route) {
                            Signup(navController = navController)
                        }
                        composable(Screen.Main.route) {
                            MainScreen(meViewmodel=meViewModel)
                        }

                    }
}

            }
        }
    }
}
