package com.deltasquad.platescanapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import com.deltasquad.platescanapp.presentation.components.*
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    var query by remember { mutableStateOf("") }

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
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                SearchBar(
                    query = query,
                    onQueryChanged = { query = it },
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                ButtonGroup()
            }

            item {
                SectionLabel(text = "Registros Recientes")
            }
            item {
                ContentCardGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
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

