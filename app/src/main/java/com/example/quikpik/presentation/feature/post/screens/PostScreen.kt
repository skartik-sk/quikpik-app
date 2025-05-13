package com.example.quikpik.presentation.feature.post.screens



// Rest of your imports...
import android.R.attr.fontWeight
import android.R.attr.onClick
import android.R.attr.text
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.quikpik.R
import com.example.quikpik.common.Screen
import com.example.quikpik.presentation.components.CustomLoading
import com.example.quikpik.presentation.components.CustomToast
import com.example.quikpik.presentation.components.PrimaryButton
import com.example.quikpik.presentation.feature.post.viewmodels.PostViewModel


@Composable
fun PostScreen(
    navController: NavHostController,

    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var comment by remember { mutableStateOf("") }
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    LaunchedEffect(Unit) {
        imagePickerLauncher.launch("image/*")
    }
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank()) {
Log.d("PostScreen", "Error: ${state.error}")
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }
if(state.isLoading){
    CustomLoading()
}else{
    if(state.isSuccess){
        CustomToast("Post uploaded successfully",false) {state.isSuccess = false }
        viewModel.clearState()
        navController.navigate(Screen.Profile.route)

    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Text(
         text = "Create Post",
         style = MaterialTheme.typography.headlineMedium,
         fontWeight = FontWeight.Bold,
         color = MaterialTheme.colorScheme.primary
     )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Placeholder Image",
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(

           text="Select Image",
        ) { imagePickerLauncher.launch("image/*") }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Add a comment") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(text="Post")
         {
                if (selectedImageUri != null && comment.isNotBlank()) {
                    viewModel.uploadPost(selectedImageUri!!, comment)
                }
            }

    }
}
}

