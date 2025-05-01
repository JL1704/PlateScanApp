package com.deltasquad.platescanapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.components.CircleImageView
import com.deltasquad.platescanapp.presentation.components.UserInfo
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
) {
    val profileState by viewModel.profile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        CircleImageView(
            imageUrl = profileState?.image ?: "https://default.image.url",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        UserInfo(
            username = profileState?.username ?: "Loading...",
            email = profileState?.email ?: "",
            phone = profileState?.phone ?: ""
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onEditProfile, modifier = Modifier.fillMaxWidth()) {
                Text("Edit")
            }

            Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                Text("Log Out")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    //ProfileScreen()
}

