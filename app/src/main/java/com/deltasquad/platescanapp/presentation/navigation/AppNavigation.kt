package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(auth: FirebaseAuth, modifier: Modifier) {
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
            onLogout = { isAuthenticated = false }
        )
    } else {
        AuthNavigation(navController = navController, auth = auth)
    }
}
