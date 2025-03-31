package com.deltasquad.platescanapp.presentation.home

import BottomNavigationView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(0) } // Estado de la navegación

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Contenido principal de la pantalla
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Se expande para ocupar todo el espacio disponible
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Home Screen Content", style = MaterialTheme.typography.bodyLarge)
        }

        // Bottom Navigation View con padding para evitar que se corte
        BottomNavigationView(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
            modifier = Modifier
                .padding(WindowInsets.navigationBars.asPaddingValues()) // Corrige el corte en la parte inferior
        )
    }
}

@Preview(
    name = "HomeScreenPreview",
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    PlateScanAppTheme {
        HomeScreen()
    }
}
