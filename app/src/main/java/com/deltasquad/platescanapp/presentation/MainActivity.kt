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

/**
 * Actividad principal de la aplicación. Se encarga de inicializar las dependencias clave
 * (Firebase, ViewModels, repositorios) y de establecer el contenido de la interfaz
 * utilizando Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

    // Controlador de navegación para gestionar el flujo de pantallas
    private lateinit var navHostController: NavHostController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Firebase (necesario para usar Auth y Firestore)
        FirebaseApp.initializeApp(this)

        // Configuración de dependencias
        val firestore = FirebaseFirestore.getInstance()
        val auth = Firebase.auth
        val repository = ProfileRepository(firestore, auth)
        val profileViewModel = ProfileViewModel(repository)

        // Inicializa AuthViewModel utilizando una factory personalizada
        val authViewModel: AuthViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(auth, applicationContext)
        )[AuthViewModel::class.java]

        // Permite que el contenido se dibuje detrás de las barras del sistema
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Establece el contenido de la actividad usando Compose
        setContent {
            // Instancia de navegación para Compose Navigation
            navHostController = rememberNavController()

            // Calcula el tamaño de la ventana para una UI adaptativa
            val windowSize = calculateWindowSizeClass(activity = this)

            // Aplica el tema personalizado de la app con diseño adaptativo
            PlateScanAppTheme(windowSize = windowSize.widthSizeClass) {
                // Controlador del sistema para manipular la UI del sistema (barra de estado, etc.)
                val systemUiController = rememberSystemUiController()

                // Establece la barra de estado como transparente y con iconos claros
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Transparent,
                        darkIcons = false
                    )
                }

                // Estructura base para la UI de Compose con soporte de padding
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    // Controlador de navegación principal de la app
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