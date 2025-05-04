package com.deltasquad.platescanapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.deltasquad.platescanapp.data.model.ScanRecord

@Composable
fun VehicleDataSection(record: ScanRecord) {
    Text("Datos del Vehículo", style = MaterialTheme.typography.titleMedium)
    Text("Tipo: ${record.vehicleType}")
    Text("Color: ${record.vehicleColor}")
    Text("Marca/Modelo: ${record.makeModel}")
}