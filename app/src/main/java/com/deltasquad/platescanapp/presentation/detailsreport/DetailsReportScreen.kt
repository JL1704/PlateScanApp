package com.deltasquad.platescanapp.presentation.detailsreport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.data.model.PlateReport
import com.deltasquad.platescanapp.presentation.components.SectionLabel

@Composable
fun DetailsReportScreen(
    reportId: String,
    navController: NavController,
    viewModel: DetailsReportViewModel = viewModel()
) {
    val report by viewModel.report.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchReportById(reportId)
    }

    if (report == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

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
                    text = "Report Details",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 44.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tarjeta de contenido
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(label = "Placa", value = report!!.plate)
                    InfoRow(label = "Tipo", value = report!!.reportType)
                    InfoRow(label = "Descripción", value = report!!.description)
                    InfoRow(label = "Fecha", value = report!!.dateReported)
                    InfoRow(label = "Ubicación", value = report!!.locationReported)
                    InfoRow(label = "Estado", value = report!!.status)
                    InfoRow(label = "Reportado por", value = report!!.reporterName)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (report!!.imageEvidence.isNotBlank()) {
                Text(
                    text = "Evidencia Fotográfica",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    AsyncImage(
                        model = report!!.imageEvidence,
                        contentDescription = "Evidencia",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(top = 4.dp))
    }
}
