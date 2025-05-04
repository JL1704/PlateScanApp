package com.deltasquad.platescanapp.presentation.home

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.presentation.components.*
import com.deltasquad.platescanapp.presentation.navigation.Screen
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val scans by viewModel.latestScans.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchLatestScans()
    }

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
            SectionLabel(text = "Recent Registrations")
        }

        items(scans) { scan ->
            ContentCard(
                croppedImage = Uri.parse(scan.croppedImage),
                plate = scan.plate,
                date = scan.date,
                state = scan.state,
                onClick = {
                    navController.navigate(Screen.Details.createRoute(scan.id))
                }
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

