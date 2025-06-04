package com.deltasquad.platescanapp.presentation.editprofile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel
import com.deltasquad.platescanapp.presentation.theme.primaryGreen
import java.io.File
import java.io.FileOutputStream

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onSave: (username: String, phone: String, imageUri: Uri?) -> Unit = { _, _, _ -> },
    onCancel: () -> Unit = {}
) {
    val context = LocalContext.current
    val profileState by viewModel.profile.collectAsState()

    if (profileState == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var username by remember { mutableStateOf(profileState!!.username) }
    var phone by remember { mutableStateOf(profileState!!.phone) }
    var imageUri by remember { mutableStateOf(profileState!!.image?.toUri()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "profile_image.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            imageUri = file.toUri()
        }
    }

    // Estados de validación
    var showError by remember { mutableStateOf(false) }

    val isUsernameValid = username.isNotBlank()
    val isPhoneValid = phone.matches(Regex("^[0-9]{8,15}$")) // 8 a 15 dígitos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Imagen de perfil
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clickable { launcher.launch("image/*") }
            )
        } ?: Box(
            modifier = Modifier
                .size(120.dp)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Change Image")
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                showError = false
            },
            label = { Text("Username") },
            isError = showError && !isUsernameValid,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError && !isUsernameValid) {
            Text("Username cannot be empty", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                showError = false
            },
            label = { Text("Phone Number") },
            isError = showError && !isPhoneValid,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError && !isPhoneValid) {
            Text("Phone number must be 8–15 digits", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    if (isUsernameValid && isPhoneValid) {
                        onSave(username, phone, imageUri)
                    } else {
                        showError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                Text("Save")
            }
        }
    }
}
