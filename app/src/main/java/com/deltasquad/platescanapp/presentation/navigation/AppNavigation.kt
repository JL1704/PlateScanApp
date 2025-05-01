package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.presentation.auth.AuthViewModel
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth



@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()

    if (isAuthenticated) {
        NavigationWrapper(
            modifier = modifier,
            auth = authViewModel.getCurrentUser()?.let { FirebaseAuth.getInstance() } ?: FirebaseAuth.getInstance(),
            rootNavController = navController,
            viewModel = profileViewModel,
            onLogout = {
                authViewModel.signOut()
            }
        )
    } else {
        AuthNavigation(
            navController = navController,
            authViewModel = authViewModel,
            viewModel = profileViewModel
        )
    }
}

