package com.deltasquad.platescanapp.data.api

import com.deltasquad.platescanapp.data.model.PlateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Interfaz que define el servicio de API para la detección de placas vehiculares.
 * Utiliza Retrofit para enviar una imagen al backend y recibir una respuesta con la información de la placa detectada.
 */
interface PlateApiService {

    /**
     * Realiza una solicitud POST al endpoint "/detect" para enviar una imagen y detectar la placa vehicular en ella.
     *
     * @param image Imagen enviada como parte de un formulario multipart. Se espera que sea una foto de un vehículo que contenga una placa visible.
     * @return [Response]<[PlateResponse]> que contiene los datos de la placa detectada, o un error si la detección falla.
     */
    @Multipart
    @POST("/detect")
    suspend fun detectPlate(@Part image: MultipartBody.Part): Response<PlateResponse>
}

