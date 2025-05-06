package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.deltasquad.platescanapp.presentation.editdata.EditDataScreen
import com.deltasquad.platescanapp.presentation.editprofile.EditProfileScreen
import com.deltasquad.platescanapp.presentation.home.HomeScreen
import com.deltasquad.platescanapp.presentation.profile.ProfileScreen
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel
import com.deltasquad.platescanapp.presentation.records.RecordsScreen
import com.deltasquad.platescanapp.presentation.reports.ReportsScreen
import com.deltasquad.platescanapp.presentation.stats.StatsScreen
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val screens = listOf(Screen.Home, Screen.Camera, Screen.Profile)
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val selectedIndex = screens.indexOfFirst { it.route == currentRoute }

    LaunchedEffect(Unit) {
        onProfileSync()
    }

    // Envuelve Scaffold con ModalNavigationDrawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        /*drawerContent = {
            // Aquí defines el contenido del menú lateral
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))

                // Agrega más ítems si lo necesitas
            }
        }*/
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(32.dp))

                Spacer(modifier = Modifier.height(128.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = "https://cdn-icons-png.flaticon.com/512/8462/8462143.png",//"https://via.placeholder.com/150",
                        contentDescription = "Work in progress",
                        modifier = Modifier
                            .size(200.dp)
                    )

                    // Aquí puedes agregar más ítems del menú si deseas
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                PSTopAppBar(onMenuClick = {
                    coroutineScope.launch { drawerState.open() } // Abre el drawer al hacer clic
                })
            },
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
                                    navController.popBackStack()
                                } else {
                                    // Mostrar error
                                }
                            }
                        },
                        onCancel = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(Screen.Records.route) { RecordsScreen(navController) }
                composable(Screen.Reports.route) { ReportsScreen(navController) }
                composable(Screen.Stats.route) { StatsScreen() }

                composable("details/{scanId}") { backStackEntry ->
                    val scanId = backStackEntry.arguments?.getString("scanId") ?: ""
                    DetailsScreen(scanId = scanId, navController)
                }

                composable(Screen.EditData.route) { backStackEntry ->
                    val scanId = backStackEntry.arguments?.getString("scanId") ?: ""
                    EditDataScreen(scanId = scanId, navController = navController)
                }
            }
        }
    }
}




