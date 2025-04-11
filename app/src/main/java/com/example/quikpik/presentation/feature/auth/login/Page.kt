package com.example.quikpik.presentation.feature.auth.login

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
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
fun Login(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
//    Log.d("Login", "Login: "+ loginViewModel.state.value)
    var showToast = remember { mutableStateOf(false) }

     if (loginViewModel.state.value.isLoading){
      return  CustomLoading()
    }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(1f).fillMaxHeight()
    ) {
        PrimaryTextField(Icons.Default.Person, "Username", username);
        Column (
            horizontalAlignment = Alignment.End
        ) {

        PrimaryTextField(LineAwesomeIcons.AsteriskSolid, "Password", password)
        Text(
            text = "Forgot Password?",
            color = MaterialTheme.colorScheme.inverseOnSurface
        )
        }
        Spacer(modifier = Modifier.size(10.dp))
Row {

    PrimaryButton ("Login")
       {

            loginViewModel.login(username.value, password.value)


        }

}
        Spacer(modifier = Modifier.size(5.dp))


        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier.padding(16.dp).clickable(true, onClick = {
                navController.navigate(Screen.Signup.route)
            }),
            color = Color.Black,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                ) {
                    append("Don't have an account?")
                }
                withStyle(
                    style = SpanStyle(
                        color = UserBlue
                    )
                ) {
                    append(" Sign Up")
                }
            }
        )



    }
    if(
        loginViewModel.state.value.error != ""
    ) {
        showToast.value = true

        if(showToast.value)  CustomToast(loginViewModel.state.value.error,true, onDismiss = {showToast.value= false})
    }
    else if(
        loginViewModel.state.value.message != ""
    ) {
        showToast.value=true
        if(showToast.value) CustomToast("Login Success",false, onDismiss = {showToast.value= false})
        navController.navigate(Screen.Main.route)
    }
}


@Preview(showBackground = true, showSystemUi = true,
    backgroundColor = 0xFF000000
)
@Composable
private fun PreLogin() {
    QuikPikTheme{

    //Login()
    }
}


