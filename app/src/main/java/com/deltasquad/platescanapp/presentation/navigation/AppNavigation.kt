package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(auth: FirebaseAuth, modifier: Modifier) {
    val navController = rememberNavController()
    val user = auth.currentUser

    if (user == null) {
        // Usuario no autenticado → flujo de login
        AuthNavigation(navController, auth)
    } else {
        // Usuario autenticado → flujo principal
        AuthNavigation(navController, auth)
        //NavigationWrapper(modifier)
    }
}
