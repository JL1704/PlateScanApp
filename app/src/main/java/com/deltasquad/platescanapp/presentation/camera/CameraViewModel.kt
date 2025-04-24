package com.deltasquad.platescanapp.presentation.camera

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CameraViewModel : ViewModel() {
    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri: StateFlow<Uri?> = _photoUri

    var imageCapture: ImageCapture? = null

    fun onPhotoCaptured(uri: Uri) {
        _photoUri.value = uri
    }
}