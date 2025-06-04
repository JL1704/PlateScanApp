package com.deltasquad.platescanapp.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.detailsreport.DetailsReportScreen
import com.deltasquad.platescanapp.presentation.reports.CreateReportScreen
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerTonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, top = 16.dp, bottom = 16.dp, end = 8.dp)
                ) {
                    Text(
                        text = "Menú",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    DrawerItem(
                        label = "Reports",
                        iconRes = R.drawable.ic_reports,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Screen.Reports.route)
                        }
                    )

                    DrawerItem(
                        label = "Records",
                        iconRes = R.drawable.ic_history,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Screen.Records.route)
                        }
                    )

                    DrawerItem(
                        label = "Stats",
                        iconRes = R.drawable.ic_stats,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Screen.Stats.route)
                        }
                    )
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
                composable(Screen.Stats.route) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
                    StatsScreen(navController = navController, userId = userId)
                }

                composable("details/{scanId}") { backStackEntry ->
                    val scanId = backStackEntry.arguments?.getString("scanId") ?: ""
                    DetailsScreen(scanId = scanId, navController)
                }

                composable(Screen.EditData.route) { backStackEntry ->
                    val scanId = backStackEntry.arguments?.getString("scanId") ?: ""
                    EditDataScreen(scanId = scanId, navController = navController)
                }
                composable(Screen.CreateReport.route) {
                    CreateReportScreen(navController)
                }
                composable(
                    route = Screen.DetailsReport.route,
                    arguments = listOf(navArgument("reportId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
                    DetailsReportScreen(reportId = reportId, navController = navController)
                }

            }
        }
    }
}

@Composable
fun DrawerItem(
    label: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 0.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start // Alinea a la izquierda
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}