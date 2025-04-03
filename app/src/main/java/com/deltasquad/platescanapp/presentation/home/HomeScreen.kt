package com.deltasquad.platescanapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import com.deltasquad.platescanapp.presentation.components.*
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
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Barra de búsqueda
            SearchBar(
                query = query,
                onQueryChanged = { query = it },
                modifier = Modifier.padding(16.dp)
            )

            // Grupo de botones
            ButtonGroup()

            // Sección "Registros Recientes"
            SectionLabel(text = "Registros Recientes")

            // Lista de tarjetas (ContentCardGroup)
            ContentCardGroup(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio restante
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
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

