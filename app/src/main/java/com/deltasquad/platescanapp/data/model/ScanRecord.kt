package com.deltasquad.platescanapp.data.model

data class ScanRecord(
    val id: String = "",
    val plate: String = "AAA-123-A",           // Placa final consolidada
    val ocrPlate: String = "",                 // Placa detectada por ML Kit OCR
    val backendPlate: String = "",             // Placa detectada por el backend
    val image: String = "",
    val croppedImage: String = "",
    val date: String = "",
    val location: String = "",
    val state: String = "success",             // "success" o "error"
    val user: String = "",
    val vehicleType: String = "",
    val vehicleColor: String = "",
    val makeModel: String = "",
    val purposeScanning: String = "",
    val comments: String = ""
)

