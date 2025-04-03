package com.deltasquad.platescanapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import com.deltasquad.platescanapp.presentation.components.PSTopAppBar
import com.deltasquad.platescanapp.presentation.components.SearchBar
import com.deltasquad.platescanapp.presentation.components.BottomNavigationView
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    var query by remember { mutableStateOf("") } // Estado para la búsqueda

    Scaffold(
        topBar = {
            PSTopAppBar(onMenuClick = { /* acción del menú */ })
        },
        bottomBar = {
            BottomNavigationView(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        },
        contentWindowInsets = WindowInsets(0.dp) // Evita que el contenido empuje la barra de navegación
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // SearchBar debajo del PSTopAppBar
            SearchBar(
                query = query,
                onQueryChanged = { query = it },
                modifier = Modifier.padding(16.dp) // Ajusta el margen según sea necesario
            )

            // El contenido principal de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Home Screen Content", style = MaterialTheme.typography.bodyLarge)
            }
        }
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

