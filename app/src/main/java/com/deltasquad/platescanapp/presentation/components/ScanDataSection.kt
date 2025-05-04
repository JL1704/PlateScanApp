package com.deltasquad.platescanapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.deltasquad.platescanapp.data.model.ScanRecord

@Composable
fun ScanDataSection(record: ScanRecord) {
    Text("Datos del Escaneo", style = MaterialTheme.typography.titleMedium)
    Text("Placa: ${record.plate}")
    Text("Fecha: ${record.date}")
    Text("Ubicación: ${record.location}")
    Text("Estado: ${record.state}")
    Text("Usuario: ${record.user}")
    Text("Propósito: ${record.purposeScanning}")
}