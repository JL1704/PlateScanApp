package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.data.model.ScanRecord

@Composable
fun VehicleDataSection(record: ScanRecord, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Vehicle Data",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        VehicleDataItem(label = "Type", value = record.vehicleType)
        VehicleDataItem(label = "Color", value = record.vehicleColor)
        VehicleDataItem(label = "Make/Model", value = record.makeModel)
    }
}

@Composable
private fun VehicleDataItem(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value ?: "Unavailable",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
