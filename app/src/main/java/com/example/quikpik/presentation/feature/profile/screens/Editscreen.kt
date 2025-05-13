package com.example.quikpik.presentation.feature.profile.screens

import android.R.attr.contentDescription
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.quikpik.R
import com.example.quikpik.presentation.common.MeViewmodle
import com.example.quikpik.presentation.components.PrimaryButton
import com.example.quikpik.presentation.components.PrimaryTextField
import androidx.core.net.toUri
import com.example.quikpik.common.Screen
import com.example.quikpik.presentation.components.CustomLoading
import com.example.quikpik.presentation.components.CustomToast
import kotlinx.coroutines.delay

@Composable
fun EditProfileScreen(
    navController: NavHostController,
    meViewModel: MeViewmodle = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val userState = meViewModel.state.collectAsState().value.userData
    val meState = meViewModel.state.collectAsState().value


    val username = remember { mutableStateOf(userState?.username ?: "") }
    val bio = remember { mutableStateOf(userState?.bio ?: "") }
    val gender = remember { mutableStateOf(userState?.gender?:" ")}
    val profilePicture = remember{ mutableStateOf(if(userState?.profileImage?.isNotEmpty() == true) userState.profileImage else "https://img.freepik.com/premium-vector/avatar-profile-icon-flat-style-male-user-profile-vector-illustration-isolated-background-man-profile-sign-business-concept_157943-38764.jpg?semt=ais_hybrid")}
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var showToast = remember { mutableStateOf(false) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    if (meState.isLoading) {
        return CustomLoading()
    }
// Replace the selected error handling code in Login page
    LaunchedEffect(meState) {
        when {
            meState.error.isNotEmpty() -> {
                showToast.value = true
                delay(3000) // Auto-dismiss after 3 seconds

            }
            meState.userData!=null -> {
                showToast.value = true
                delay(1500) // Show success message briefly
                navController.navigate(Screen.Profile.route)

            }
        }
    }
    if (showToast.value) {
        CustomToast(
            message = if (meState.error.isNotEmpty()) meState.error else "Login Success",
            isError = meState.error.isNotEmpty(),
            onDismiss = { showToast.value = false }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {


            Text("Edit Profile", fontSize = 24.sp)
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
                        painter = rememberAsyncImagePainter(profilePicture.value.toString()),
                        contentDescription = "Placeholder Image",
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton(

                text = "Select Image",
            ) { imagePickerLauncher.launch("image/*") }
        }
        item {


            Spacer(modifier = Modifier.height(8.dp))


            PrimaryTextField(
                icon = Icons.Outlined.Person,
                lable = "Username",
                username = username

            )

            PrimaryTextField(
                icon = Icons.Default.Create,
                lable = "Bio",
                username = bio,
            )
            PrimaryTextField(
                icon = Icons.Default.Face,
                lable = "Gender",
                username = gender
            )

            PrimaryButton("Update Profile") {
                meViewModel.updateUserData(
                    imageUri = selectedImageUri ?: profilePicture.value.toString().toUri(),
                    username = username.value,
                    bio = bio.value,
                    gender = gender.value
                )
            }

        }
    }
}