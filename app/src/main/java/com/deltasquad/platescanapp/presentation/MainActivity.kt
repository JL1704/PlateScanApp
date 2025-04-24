package com.deltasquad.platescanapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.deltasquad.platescanapp.presentation.components.ContentCard
import com.deltasquad.platescanapp.presentation.home.HomeScreen
import com.deltasquad.platescanapp.presentation.navigation.NavigationWrapper
import com.deltasquad.platescanapp.presentation.profile.ProfileScreen
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false) // Solo se ejecuta en Android 11+
        } //  Evita solapamiento con la barra del sistema

        setContent {
            val windowSize = calculateWindowSizeClass(activity = this)
            PlateScanAppTheme(
                windowSize = windowSize.widthSizeClass
            ) {
                NavigationWrapper()
                //ProfileScreen()
                //HomeScreen()
                //TestScreen()

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


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    PlateScanAppTheme {
        HomeScreen()
    }
}