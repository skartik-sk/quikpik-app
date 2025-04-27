package com.example.quikpik.presentation.components

import android.R.attr.contentDescription
import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quikpik.presentation.ui.theme.LightBlue
import com.example.quikpik.presentation.ui.theme.SuperLightBlue

@Composable
fun PrimaryTextField(icon: ImageVector,lable:String ,username:  MutableState<String>) {
   return TextField(


        value = username.value,
        onValueChange = { username.value  = it },
        label = { Text(lable, color = MaterialTheme.colorScheme.onPrimary)  },
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, LightBlue,).clip(
                MaterialTheme.shapes.large
            ),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary

        ),
        leadingIcon = {
            androidx.compose.material3.Icon(
                modifier = Modifier.size(20.dp),
                imageVector = icon,
                contentDescription = "Username Icon",
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        },


        )

}
