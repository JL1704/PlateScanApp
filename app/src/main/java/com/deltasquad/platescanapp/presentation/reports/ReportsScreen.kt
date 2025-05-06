package com.deltasquad.platescanapp.presentation.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.components.*
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.presentation.navigation.Screen
import com.deltasquad.platescanapp.presentation.theme.*

@Composable
fun ReportsScreen(
    navController: NavHostController,
    viewModel: ReportsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val reports by viewModel.allReports.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllReports()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_24),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                SectionLabel(
                    text = "Reports",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 44.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de reportes
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(reports.size) { index ->
                    ReportCard(
                        report = reports[index],
                        onClick = {
                            navController.navigate(Screen.DetailsReport.createRoute(reports[index].id))
                        }
                    )
                }
            }
        }

        // FAB flotante
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.CreateReport.route)
            },
            shape = CircleShape,
            containerColor = primaryGreen,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 24.dp, end = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Crear reporte",
                tint = Color.White
            )
        }
    }
}
