package com.deltasquad.platescanapp.presentation.editprofile

import android.net.Uri
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

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onSave: (username: String, phone: String, imageUri: Uri?) -> Unit = { _, _, _ -> },
    onCancel: () -> Unit = {}
) {
    val profileState by viewModel.profile.collectAsState()

    // Esperamos a que cargue el perfil
    if (profileState == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var username by remember { mutableStateOf(profileState!!.username) }
    var phone by remember { mutableStateOf(profileState!!.phone) }
    var imageUri by remember { mutableStateOf<Uri?>(profileState!!.image?.toUri()) }

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
                    .clickable {
                        // Aquí irá el selector de imagen
                    }
            )
        } ?: Box(
            modifier = Modifier
                .size(120.dp)
                .clickable {
                    // Selector de imagen
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Username
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Phone
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botones
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
                    onSave(username, phone, imageUri)

                },
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                Text("Save")
            }
        }
    }
}
