package com.example.quikpik.presentation.components

import android.R.attr.contentDescription
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.quikpik.presentation.ui.theme.LightBlue
import com.example.quikpik.presentation.ui.theme.SuperLightBlue

@Composable
fun PrimaryTextField(icon: ImageVector,lable:String ,username:  MutableState<String>) {
   return TextField(

        value = username.value,
        onValueChange = { username.value  = it },
        label = { Text(lable)  },
        modifier = Modifier.padding(16.dp)
            .border(1.dp, LightBlue,),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = SuperLightBlue,
            unfocusedContainerColor = SuperLightBlue,

            cursorColor = Color.Black
        ),
        leadingIcon = {
            androidx.compose.material3.Icon(
                modifier = Modifier.size(20.dp),
                imageVector = icon,
                contentDescription = "Username Icon"
            )
        },


        )

}