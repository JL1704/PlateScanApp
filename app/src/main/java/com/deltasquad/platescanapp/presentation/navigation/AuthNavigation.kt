package com.deltasquad.platescanapp.presentation.navigation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deltasquad.platescanapp.presentation.initial.InitialScreen
import com.deltasquad.platescanapp.presentation.login.LoginScreen
import com.deltasquad.platescanapp.presentation.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import com.deltasquad.platescanapp.presentation.auth.AuthViewModel
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel

@Composable
fun AuthNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            authViewModel.handleSignInResult(
                data,
                onSuccess = {
                    navController.navigate("main") {
                        popUpTo("initial") { inclusive = true }
                    }
                },
                onFailure = { /* Mostrar error si se desea */ }
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) "main" else "initial"
    ) {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navController.navigate("logIn") },
                navigateToSignUp = { navController.navigate("signUp") },
                onGoogleSignInClick = {
                    activity?.let {
                        authViewModel.signInWithGoogle(it) { request ->
                            launcher.launch(request)
                        }
                    }
                }
            )
        }

        composable("logIn") {
            LoginScreen(
                auth = FirebaseAuth.getInstance(),
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("initial") { inclusive = true }
                    }
                }
            )
        }

        composable("signUp") {
            SignUpScreen(
                auth = FirebaseAuth.getInstance(),
                navController = navController,
                onSignUpSuccess = {
                    navController.navigate("main") {
                        popUpTo("initial") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            NavigationWrapper(
                auth = FirebaseAuth.getInstance(),
                rootNavController = navController,
                viewModel = viewModel,
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate("initial") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}
