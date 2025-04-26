package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.runtime.Composable
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
    var currentUser by remember { mutableStateOf(auth.currentUser) }


    if (currentUser == null) {
        AuthNavigation(navController, auth)
    } else {
        NavigationWrapper(modifier = modifier, auth = auth, rootNavController = navController)
    }
}

