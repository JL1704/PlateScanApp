package com.deltasquad.platescanapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deltasquad.platescanapp.data.repository.ProfileRepository
import com.deltasquad.platescanapp.presentation.auth.AuthViewModel
import com.deltasquad.platescanapp.presentation.auth.AuthViewModelFactory
import com.deltasquad.platescanapp.presentation.navigation.AppNavigation
import com.deltasquad.platescanapp.presentation.profile.ProfileViewModel
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        // Inicializamos dependencias necesarias
        val firestore = FirebaseFirestore.getInstance()
        val auth = Firebase.auth
        val repository = ProfileRepository(firestore, auth)
        val profileViewModel = ProfileViewModel(repository)

        // ViewModel de autenticación con Factory
        val authViewModel: AuthViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(auth, applicationContext)
        )[AuthViewModel::class.java]

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            navHostController = rememberNavController()
            val windowSize = calculateWindowSizeClass(activity = this)

            PlateScanAppTheme(windowSize = windowSize.widthSizeClass) {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Transparent,
                        darkIcons = false
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    AppNavigation(
                        navController = navHostController,
                        modifier = Modifier.padding(paddingValues),
                        authViewModel = authViewModel,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}


/*
@Composable
fun TestScreen() {
    ContentCard(
        imageUrl = "https://picsum.photos/200/300",
        plateNumber = "1234 ABC",
        date = "01/01/2025",
        description = "Prueba de tarjeta",
        onClick = {}
    )
}

@Composable
fun TestCrashlytics(name: String, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
            throw RuntimeException("Esto es una prueba")
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    PlateScanAppTheme {
        //HomeScreen()
    }
}*/