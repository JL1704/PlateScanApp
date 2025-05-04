package com.deltasquad.platescanapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.deltasquad.platescanapp.data.model.ScanRecord

@Composable
fun CommentsSection(record: ScanRecord) {
    Text("Comentarios", style = MaterialTheme.typography.titleMedium)
    Text(record.comments.ifEmpty { "Sin comentarios" })
}