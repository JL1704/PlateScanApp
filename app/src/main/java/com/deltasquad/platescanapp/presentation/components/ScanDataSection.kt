package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.data.model.ScanRecord

@Composable
fun ScanDataSection(record: ScanRecord, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Scan Data",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        ScanDataItem(label = "Plate", value = record.plate)
        ScanDataItem(label = "Date", value = record.date)
        ScanDataItem(label = "Location", value = record.location)
        ScanDataItem(label = "State", value = record.state)
        ScanDataItem(label = "User", value = record.user)
        ScanDataItem(label = "Purpose of Scanning", value = record.purposeScanning)
    }
}

@Composable
private fun ScanDataItem(label: String, value: String?) {
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
