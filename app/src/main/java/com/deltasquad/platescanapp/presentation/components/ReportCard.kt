package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


data class Report(
    val plate: String,
    val type: String,
    val description: String
)

@Composable
fun ReportCard(
    report: Report,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Placa: ${report.plate}", fontWeight = FontWeight.Bold)
            Text(text = "Tipo de reporte: ${report.type}", color = Color.Gray)
            Text(text = "Descripción: ${report.description}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportCardPreview() {
    ReportCard(
        report = Report(
            plate = "AAA-000-A",
            type = "Accidente",
            description = "El auto con esta placa se accidentó"
        ),
        onClick = {}
    )
}
