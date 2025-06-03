package com.deltasquad.platescanapp.data.model

data class PlateResponse(
    val box: List<Float>?,     // Puede ser null si no se detecta nada
    val success: Boolean,
    val message: String? = null  // Opcional, útil para mostrar errores del backend
)


