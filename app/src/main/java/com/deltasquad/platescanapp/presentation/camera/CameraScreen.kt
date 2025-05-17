package com.deltasquad.platescanapp.presentation.camera

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
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.Color
import android.graphics.Matrix
import android.view.Surface
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.deltasquad.platescanapp.presentation.theme.primaryGreen
import com.deltasquad.platescanapp.presentation.theme.secondaryGreen

@Composable
fun CameraScreen(viewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Vista de la cámara
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        // Marco guía (superpuesto)
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            ScanFrame() // Usamos el nuevo marco estilizado
        }

        // Botón para capturar la imagen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    takePhotoAndCrop(context, previewView, viewModel) { uri ->
                        photoUri = uri
                        Toast.makeText(context, "Photo captured: $uri", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(Color.Cyan)
            ) {
                Text("Scan Plate", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }

    // Configurar la cámara
    LaunchedEffect(true) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()

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
}

fun takePhotoAndCrop(
    context: Context,
    previewView: PreviewView,
    viewModel: CameraViewModel,
    onPhotoCaptured: (Uri) -> Unit
) {
    val imageCapture = viewModel.imageCapture ?: return
    val tempFile = File(context.cacheDir, "IMG_${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(tempFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                try {
                    var bitmap = BitmapFactory.decodeFile(tempFile.absolutePath)

                    // 🔁 Corregir rotación
                    val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                        .defaultDisplay.rotation

                    val matrix = Matrix()
                    when (rotation) {
                        Surface.ROTATION_0 -> matrix.postRotate(90f)
                        Surface.ROTATION_270 -> matrix.postRotate(180f)
                        // Puedes ajustar más según el comportamiento de tu dispositivo
                    }

                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                    // Tamaño real del bitmap corregido
                    val bitmapWidth = bitmap.width
                    val bitmapHeight = bitmap.height

                    // 🟥 Calculamos el recorte centrado
                    val cropWidth = (bitmapWidth * 0.6).toInt()
                    val cropHeight = (bitmapHeight * 0.2).toInt()
                    val cropX = (bitmapWidth - cropWidth) / 2
                    val cropY = (bitmapHeight - cropHeight) / 2

                    val croppedBitmap = Bitmap.createBitmap(bitmap, cropX, cropY, cropWidth, cropHeight)

                    // Guardar imagen original y recortada
                    val originalUri = saveBitmapToGallery(context, bitmap, "original_${tempFile.name}")
                    val croppedUri = saveBitmapToGallery(context, croppedBitmap, "cropped_${tempFile.name}")

                    viewModel.onPhotoCaptured(originalUri!!)
                    onPhotoCaptured(croppedUri!!)

                    // Guardar en Firestore 🔥
                    viewModel.sendCroppedImageToServer(context, originalUri, croppedUri)
                    //viewModel.saveScanRecord(originalUri, croppedUri)
                    //viewModel.sendCroppedImageToServer(context, croppedUri)
                    //viewModel.saveScanRecordWithoutLocation(originalUri, croppedUri)

                    tempFile.delete()
                } catch (e: Exception) {
                    Log.e("CameraCapture", "Error al recortar o guardar", e)
                    Toast.makeText(context, "Error processing the image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraCapture", "Error al capturar foto", exception)
                Toast.makeText(context, "Error Capturing Photo", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

fun saveBitmapToGallery(context: Context, bitmap: Bitmap, name: String): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/PlateScanApp")
        }
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        resolver.openOutputStream(it)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    }

    return uri
}

@Composable
fun CameraScreenEntryPoint() {
    RequestPermissions {
        CameraScreen()
    }
}


@Composable
fun ScanFrame(
    width: Dp = 300.dp,
    height: Dp = 100.dp,
    color: Color = Color.Cyan,
    strokeWidth: Dp = 4.dp,
    cornerLength: Dp = 24.dp
) {
    Box(
        modifier = Modifier
            .size(width, height)
    ) {
        val borderModifier = Modifier
            .absoluteOffset()
            .width(strokeWidth)
            .height(cornerLength)
            .background(color)

        val horizontalBorderModifier = Modifier
            .absoluteOffset()
            .height(strokeWidth)
            .width(cornerLength)
            .background(color)

        // Esquina superior izquierda
        Box(modifier = borderModifier.align(Alignment.TopStart))
        Box(modifier = horizontalBorderModifier.align(Alignment.TopStart))

        // Esquina superior derecha
        Box(modifier = borderModifier.align(Alignment.TopEnd))
        Box(modifier = horizontalBorderModifier.align(Alignment.TopEnd))

        // Esquina inferior izquierda
        Box(modifier = borderModifier.align(Alignment.BottomStart))
        Box(modifier = horizontalBorderModifier.align(Alignment.BottomStart))

        // Esquina inferior derecha
        Box(modifier = borderModifier.align(Alignment.BottomEnd))
        Box(modifier = horizontalBorderModifier.align(Alignment.BottomEnd))
    }
}
