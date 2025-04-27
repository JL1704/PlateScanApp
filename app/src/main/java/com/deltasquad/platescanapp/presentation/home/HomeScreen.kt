package com.deltasquad.platescanapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.presentation.components.*
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun HomeScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp)
    ) {
        item {
            SearchBar(
                query = query,
                onQueryChanged = { query = it }
            )
        }

        item {
            ButtonGroup(navController = navController)
        }

        item {
            SectionLabel(text = "Registros Recientes")
        }

        item {
            ContentCardGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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
        //HomeScreen()
    }
}

