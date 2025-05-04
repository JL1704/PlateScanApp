package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deltasquad.platescanapp.presentation.camera.CameraScreenEntryPoint
import com.deltasquad.platescanapp.presentation.components.BottomNavigationView
import com.deltasquad.platescanapp.presentation.components.PSTopAppBar
import com.deltasquad.platescanapp.presentation.details.DetailsScreen
import com.deltasquad.platescanapp.presentation.editprofile.EditProfileScreen
import com.deltasquad.platescanapp.presentation.home.HomeScreen
import com.deltasquad.platescanapp.presentation.profile.ProfileScreen
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel
import com.deltasquad.platescanapp.presentation.records.RecordsScreen
import com.deltasquad.platescanapp.presentation.reports.ReportsScreen
import com.deltasquad.platescanapp.presentation.stats.StatsScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    rootNavController: NavHostController,
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onProfileSync: () -> Unit
) {
    val navController = rememberNavController()

    val screens = listOf(Screen.Home, Screen.Camera, Screen.Profile)
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val selectedIndex = screens.indexOfFirst { it.route == currentRoute }//.coerceAtLeast(0)

    LaunchedEffect(Unit) {
        onProfileSync() // 👈 Solo una vez al entrar al wrapper
    }

    Scaffold(
        topBar = { PSTopAppBar(onMenuClick = { }) },
        bottomBar = {
            if (selectedIndex >= 0) {
                BottomNavigationView(
                    selectedItem = selectedIndex,
                    onItemSelected = { index ->
                        val screen = screens[index]
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Camera.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Camera.route) { CameraScreenEntryPoint() }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    //auth = auth,
                    viewModel = viewModel,
                    onLogout = onLogout,
                    onEditProfile = {
                        navController.navigate(Screen.EditProfile.route)
                    }
                    )
            }
            composable(Screen.EditProfile.route) {
                EditProfileScreen(
                    viewModel = viewModel,
                    onSave = { username, phone, imageUri ->
                        val imageUrl = imageUri?.toString()
                        viewModel.updateUserProfile(username, phone, imageUrl) { success ->
                            if (success) {
                                navController.popBackStack() // 👈 usa este, no rootNavController
                            } else {
                                // Mostrar error
                            }
                        }
                    },
                    onCancel = {
                        navController.popBackStack() // 👈 usa este también
                    }
                )
            }
            composable(Screen.Records.route) { RecordsScreen(navController) }
            composable(Screen.Reports.route) { ReportsScreen() }
            composable(Screen.Stats.route) { StatsScreen() }

            composable("details/{scanId}") { backStackEntry ->
                val scanId = backStackEntry.arguments?.getString("scanId") ?: ""
                DetailsScreen(scanId = scanId)
            }

        }
    }
}



