package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deltasquad.platescanapp.presentation.camera.CameraScreen
import com.deltasquad.platescanapp.presentation.components.BottomNavigationView
import com.deltasquad.platescanapp.presentation.home.HomeScreen
import com.deltasquad.platescanapp.presentation.profile.ProfileScreen
import androidx.compose.ui.Modifier
import com.deltasquad.platescanapp.presentation.camera.CameraScreenEntryPoint
import com.deltasquad.platescanapp.presentation.components.PSTopAppBar

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val screens = listOf(Screen.Home, Screen.Camera, Screen.Profile)
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val selectedIndex = screens.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    Scaffold(
        topBar = {
            PSTopAppBar(onMenuClick = { /* acción del menú */ })
        },
        bottomBar = {
            BottomNavigationView(
                selectedItem = selectedIndex,
                onItemSelected = { index ->
                    val screen = screens[index]
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Camera.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Camera.route) {
                CameraScreenEntryPoint()
            }
/*
            composable(Screen.Camera.route) {
                CameraScreen()
            }*/
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}


