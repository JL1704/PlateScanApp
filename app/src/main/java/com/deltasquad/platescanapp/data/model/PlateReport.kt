package com.deltasquad.platescanapp.data.model

data class PlateReport(
    val id: String = "",
    val scanRecordId: String = "", // Referencia al ScanRecord
    val plate: String = "", // Redundante pero útil para búsquedas rápidas
    val reportType: String = "", // Tipo de reporte (vehículo sospechoso, mal estacionado, robado, etc.)
    val description: String = "", // Descripción libre del usuario
    val dateReported: String = "", // Fecha y hora del reporte
    val reporterId: String = "", // UID del usuario que reporta
    val reporterName: String = "", // Nombre visible del usuario (si aplica)
    val locationReported: String = "", // Ubicación al momento de reportar (puede o no coincidir con el scan)
    val imageEvidence: String = "", // Imagen opcional (puede ser diferente a la del escaneo)
    val status: String = "pending" // Estado del reporte: pending, reviewed, dismissed, etc.
)
