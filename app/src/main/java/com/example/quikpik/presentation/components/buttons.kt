package com.example.quikpik.presentation.components

import android.R.attr.enabled
import android.R.attr.onClick
import android.R.attr.text
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.request.colorSpace
import com.example.quikpik.presentation.ui.theme.QuikPikTheme

enum class
ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

@Composable
fun PrimaryButton(text:String, verient: ButtonType= ButtonType.PRIMARY , onclick:()->Unit) {
    Button(
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (verient) {
                ButtonType.PRIMARY -> MaterialTheme.colorScheme.primary
                ButtonType.SECONDARY -> MaterialTheme.colorScheme.secondary
                ButtonType.TERTIARY -> MaterialTheme.colorScheme.tertiary
            },
            contentColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(fraction = 0.5f),
        onClick = onclick,
    ) {
        Text(text= text)
    }

}

@Preview(showSystemUi = true,

    )
@Composable
private fun PrePrimaryButton() {
    QuikPikTheme {

    Row {

     PrimaryButton("Login", verient = ButtonType.SECONDARY) { }
    }

    }
}