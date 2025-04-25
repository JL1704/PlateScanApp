package com.deltasquad.platescanapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.components.ContentCard
import com.deltasquad.platescanapp.presentation.home.HomeScreen
import com.deltasquad.platescanapp.presentation.navigation.NavigationWrapper
import com.deltasquad.platescanapp.presentation.profile.ProfileScreen
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false) // Solo se ejecuta en Android 11+
        } //  Evita solapamiento con la barra del sistema
        */
        setContent {
            val windowSize = calculateWindowSizeClass(activity = this)
            PlateScanAppTheme(
                windowSize = windowSize.widthSizeClass
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) { paddingValues ->
                    NavigationWrapper( modifier = Modifier.padding(paddingValues) )
                    //ProfileScreen()
                    //HomeScreen()
                    //TestScreen()
                    /*TestCrashlytics(
                        name = "Usuario",
                        modifier = Modifier.padding(paddingValues)
                    )*/
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
        HomeScreen()
    }
}