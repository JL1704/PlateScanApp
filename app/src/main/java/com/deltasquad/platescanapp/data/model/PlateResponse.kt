package com.deltasquad.platescanapp.data.model

data class PlateResponse(
    val plate: String,
    val success: Boolean,
    val message: String? = null
)