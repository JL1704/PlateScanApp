package com.deltasquad.platescanapp.data.model

data class ScanRecord(
    val plate: String = "AAA-123-A",
    val image: String = "",
    val croppedImage: String = "",
    val date: String = "",
    val location: String = "",
    val state: String = "success", // o "error"
    val user: String = "",
    val vehicleType: String = "",
    val vehicleColor: String = "",
    val makeModel: String = "",
    val purposeScanning: String = "",
    val comments: String = ""
)
