package com.example.quikpik.presentation.components

import android.R.attr.enabled
import android.R.attr.onClick
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
import com.example.quikpik.presentation.ui.theme.QuikPikTheme

@Composable
fun PrimaryButton( text:String, onclick:()->Unit){
    Button(
        shape = RoundedCornerShape(15),
        modifier = Modifier
//            .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
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

     PrimaryButton("Login") { }
    }

    }
}