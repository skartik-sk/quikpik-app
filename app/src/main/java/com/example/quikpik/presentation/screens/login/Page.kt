package com.example.quikpik.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quikpik.domain.usecase.AuthUseCases
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun Login(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Log.d("Login", "Login: "+ loginViewModel.state.value)
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Text(text = "Login !", modifier = Modifier.padding(16.dp))
    Column {

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.padding(16.dp)

        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.padding(16.dp)

        )
Row {


    Button(
        onClick = {

            loginViewModel.login(username.value, password.value)


        }
    ) {
        Text("Login")
    }
    Button(
        onClick = {

            loginViewModel.logout()


        }
    ) {
        Text("Logout")
    }
}
    }
}

