package com.deltasquad.platescanapp.presentation.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(
    auth: FirebaseAuth,
    modifier: Modifier,
    googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>,
    viewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

    LaunchedEffect(auth.currentUser) {
        isAuthenticated = auth.currentUser != null
    }

    if (isAuthenticated) {
        NavigationWrapper(
            modifier = modifier,
            auth = auth,
            rootNavController = navController,
            viewModel = viewModel,
            onLogout = { isAuthenticated = false }
        )
    } else {
        AuthNavigation(navController = navController, auth = auth, viewModel = viewModel)
    }
}
