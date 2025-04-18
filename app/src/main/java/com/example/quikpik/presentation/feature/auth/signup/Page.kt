package com.example.quikpik.presentation.feature.auth.signup

import android.R.attr.onClick
import android.R.attr.text
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quikpik.common.Screen
import com.example.quikpik.presentation.components.CustomLoading
import com.example.quikpik.presentation.components.CustomToast
import com.example.quikpik.presentation.components.PrimaryButton
import com.example.quikpik.presentation.components.PrimaryTextField
import com.example.quikpik.presentation.ui.theme.LightBlue
import com.example.quikpik.presentation.ui.theme.QuikPikTheme
import com.example.quikpik.presentation.ui.theme.SuperLightBlue
import com.example.quikpik.presentation.ui.theme.UserBlue
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.AccessibleIcon
import compose.icons.lineawesomeicons.AsteriskSolid


@Composable
fun Signup(
    signupViewModel: SignupViewModel = hiltViewModel(),
    navController: NavHostController
) { 
    val loginState = signupViewModel.state.collectAsState().value
    Log.d("Login", "Login: $loginState")
    var showToast = remember { mutableStateOf(false) }

    if (loginState.isLoading) {
        return CustomLoading()
    }


    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }





    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight()
    ) {
        PrimaryTextField(Icons.Default.Person, "Username", username)
        Column(
            horizontalAlignment = Alignment.End
        ) {

            PrimaryTextField(LineAwesomeIcons.AsteriskSolid, "Password", password)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row {

            PrimaryButton("Signup")
            {

                signupViewModel.Signup(username.value,email.value, password.value)

            }

        }
        Spacer(modifier = Modifier.size(5.dp))
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable(true, onClick = {
                    navController.navigate(Screen.Login.route)
                    signupViewModel.clearState()
                }),
            color = Color.Black,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                ) {
                    append("Already have an account?")
                }
                withStyle(
                    style = SpanStyle(
                        color = UserBlue
                    )
                ) {
                    append(" Login")
                }
            }
        )


        if (
            loginState.error.isNotEmpty()
        ) {
            showToast.value = true
            if (showToast.value) CustomToast(
                loginState.error,
                true,
                onDismiss = { showToast.value = false })
        } else if (
            loginState.message.isNotEmpty()
        ) {
            showToast.value = true
            if (showToast.value) CustomToast(
                "Login Success",
                false,
                onDismiss = { showToast.value = false })
            navController.navigate(Screen.Main.route)
            signupViewModel.clearState()
        }
    }

}


@Preview(
    showBackground = true, showSystemUi = true,
    backgroundColor = 0xFF000000
)
@Composable
private fun PreLogin() {
    QuikPikTheme {

        //Signup()
    }
}


