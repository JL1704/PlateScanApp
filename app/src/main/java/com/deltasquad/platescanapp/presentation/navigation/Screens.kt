package com.deltasquad.platescanapp.presentation.navigation

// com.deltasquad.platescanapp.presentation.navigation.Screens.kt
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Profile : Screen("profile")
    object EditProfile : Screen("editprofile")
    object Records : Screen("records")
    object Reports : Screen("reports")
    object Stats : Screen("stats")
}


