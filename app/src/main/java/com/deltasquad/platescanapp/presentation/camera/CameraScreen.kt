package com.deltasquad.platescanapp.presentation.camera

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File
import java.util.concurrent.Executor


@Composable
fun CameraScreen(viewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // AndroidView para mostrar la cámara
    AndroidView(
        factory = { previewView },
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp) // espacio para el botón
    )

    LaunchedEffect(true) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageCapture = ImageCapture.Builder().build()
        viewModel.imageCapture = imageCapture

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            Log.e("CameraScreen", "Error al iniciar la cámara", e)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                takePhoto(context, viewModel) { uri ->
                    photoUri = uri
                    Toast.makeText(context, "Foto capturada: $uri", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text("Capturar foto")
        }
    }
}

fun takePhoto(context: Context, viewModel: CameraViewModel, onPhotoCaptured: (Uri) -> Unit) {
    val imageCapture = viewModel.imageCapture ?: return

    val photoFile = File(
        context.cacheDir,
        "IMG_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val uri = Uri.fromFile(photoFile)
                viewModel.onPhotoCaptured(uri)
                onPhotoCaptured(uri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraCapture", "Error al capturar foto", exception)
                Toast.makeText(context, "Error al capturar foto", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
fun CameraScreenEntryPoint() {
    RequestCameraPermission {
        CameraScreen()
    }
}


/*
@Preview(
    name = "CameraScreenPreview",
    showBackground = true
)
@Composable
fun CameraScreenPreview() {
    PlateScanAppTheme {
        CameraScreen()
    }
}*/