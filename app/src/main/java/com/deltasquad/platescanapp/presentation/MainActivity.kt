package com.deltasquad.platescanapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deltasquad.platescanapp.data.auth.GoogleAuthUiClient
import com.deltasquad.platescanapp.presentation.components.ContentCard
import com.deltasquad.platescanapp.presentation.home.HomeScreen
import com.deltasquad.platescanapp.presentation.navigation.AppNavigation
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var auth: FirebaseAuth
    private lateinit var googleAuthUiClient: GoogleAuthUiClient
    //private lateinit var googleSignInLauncher: ActivityResultLauncher<IntentSender>
    private lateinit var googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        auth = Firebase.auth
        googleAuthUiClient = GoogleAuthUiClient(this)

        // Configura el launcher para manejar el resultado del inicio de sesión de Google
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                googleAuthUiClient.handleSignInResult(result.data) { success ->
                    if (success) {
                        // Aquí puedes manejar el éxito de la autenticación, redirigir a la pantalla principal, etc.
                        navHostController.navigate("home") // Ajusta esto a tu flujo de navegación
                    } else {
                        // Aquí puedes manejar el error de autenticación
                        // Mostrar un mensaje de error o intentar el proceso nuevamente
                    }
                }
            } else {
                // Maneja el caso cuando el usuario no completa el inicio de sesión
            }
        }

        setContent {
            navHostController = rememberNavController()
            val windowSize = calculateWindowSizeClass(activity = this)

            PlateScanAppTheme(windowSize = windowSize.widthSizeClass) {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Black,
                        darkIcons = true
                    )
                }

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) { paddingValues ->
                    AppNavigation(auth, modifier = Modifier.padding(paddingValues), googleSignInLauncher = googleSignInLauncher)
                }
            }
        }
    }
}

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
}