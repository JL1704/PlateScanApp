package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deltasquad.platescanapp.presentation.initial.InitialScreen
import com.deltasquad.platescanapp.presentation.login.LoginScreen
import com.deltasquad.platescanapp.presentation.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthNavigation(navController: NavHostController, auth: FirebaseAuth) {
    var isAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

    // Escuchar cambios de sesión
    LaunchedEffect(auth.currentUser) {
        isAuthenticated = auth.currentUser != null
    }

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) "main" else "initial"
    ) {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navController.navigate("logIn") },
                navigateToSignUp = { navController.navigate("signUp") }
            )
        }

        composable("logIn") {
            LoginScreen(
                auth = auth,
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
                auth = auth,
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
                auth = auth,
                rootNavController = navController,
                onLogout = {
                    isAuthenticated = false
                    navController.navigate("initial") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}

