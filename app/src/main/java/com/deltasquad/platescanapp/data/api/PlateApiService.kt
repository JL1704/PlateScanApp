package com.deltasquad.platescanapp.data.api

import com.deltasquad.platescanapp.data.model.PlateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PlateApiService {
    @Multipart
    @POST("/detect")
    suspend fun detectPlate(@Part image: MultipartBody.Part): Response<PlateResponse>
}
