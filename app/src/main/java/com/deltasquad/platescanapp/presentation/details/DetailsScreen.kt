package com.deltasquad.platescanapp.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deltasquad.platescanapp.presentation.components.CommentsSection
import com.deltasquad.platescanapp.presentation.components.ImageSection
import com.deltasquad.platescanapp.presentation.components.ScanDataSection
import com.deltasquad.platescanapp.presentation.components.VehicleDataSection

@Composable
fun DetailsScreen(
    scanId: String,
    viewModel: DetailViewModel = viewModel()
) {
    LaunchedEffect(scanId) {
        viewModel.fetchScanById(scanId)
    }

    val scanRecord by viewModel.scanRecord.collectAsState()

    scanRecord?.let { record ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScanDataSection(record)
            Spacer(modifier = Modifier.height(16.dp))
            VehicleDataSection(record)
            Spacer(modifier = Modifier.height(16.dp))
            CommentsSection(record)
            Spacer(modifier = Modifier.height(16.dp))
            ImageSection(record)
        }
    } ?: Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}
