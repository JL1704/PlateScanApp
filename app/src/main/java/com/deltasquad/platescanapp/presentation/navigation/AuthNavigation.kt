package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deltasquad.platescanapp.presentation.initial.InitialScreen
import com.deltasquad.platescanapp.presentation.login.LoginScreen
import com.deltasquad.platescanapp.presentation.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthNavigation(navController: NavHostController, auth: FirebaseAuth) {
    NavHost(navController = navController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navController.navigate("logIn") },
                navigateToSignUp = { navController.navigate("signUp") }
            )
        }
        composable("logIn") {
            LoginScreen(auth)
        }
        composable("signUp") {
            SignUpScreen(auth)
        }
    }
}
