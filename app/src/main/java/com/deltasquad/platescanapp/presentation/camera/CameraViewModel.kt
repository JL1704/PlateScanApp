package com.deltasquad.platescanapp.presentation.camera

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.lifecycle.AndroidViewModel
import com.deltasquad.platescanapp.data.api.RetrofitClient
import com.deltasquad.platescanapp.data.model.ScanRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import android.graphics.Rect

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    var imageCapture: ImageCapture? = null
    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri: StateFlow<Uri?> = _photoUri

    fun onPhotoCaptured(uri: Uri) {
        _photoUri.value = uri
    }

    @SuppressLint("MissingPermission")
    fun saveScanRecord(imageUri: Uri, croppedUri: Uri, plate: String, success: Boolean) {
        val user = auth.currentUser ?: return
        val dateStr = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val state = if (success) "success" else "failed"

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            val scanData = ScanRecord(
                plate = plate,
                image = imageUri.toString(),
                croppedImage = croppedUri.toString(),
                date = dateStr,
                location = "${location?.latitude ?: "unknown"}, ${location?.longitude ?: "unknown"}",
                state = state,
                user = user.uid
            )

            db.collection("users")
                .document(user.uid)
                .collection("scans")
                .add(scanData)
                .addOnSuccessListener {
                    Log.d("Firestore", "Scan guardado exitosamente")
                    Toast.makeText(
                        getApplication(),
                        "✅ Scan guardado en Firestore",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Log.e("Firestore", "Error al guardar el escaneo", it)
                    Toast.makeText(
                        getApplication(),
                        "❌ Error al guardar en Firestore: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    fun sendCroppedImageToServer(context: Context, originalUri: Uri, croppedUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = context.contentResolver
            var tempFile: File? = null

            try {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "📤 Enviando imagen redimensionada al servidor...", Toast.LENGTH_SHORT).show()
                }

                // 1. Leer Bitmap desde el URI
                val inputStream = contentResolver.openInputStream(originalUri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (originalBitmap == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "❌ No se pudo leer la imagen", Toast.LENGTH_LONG).show()
                    }
                    return@launch
                }

                // 2. Redimensionar a 640x640
                val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 640, 640, true)

                // 3. Guardar el Bitmap redimensionado en un archivo temporal
                tempFile = File.createTempFile("resized_", ".jpg", context.cacheDir)
                val outputStream = tempFile.outputStream()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                // 4. Preparar MultipartBody para Retrofit
                val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", tempFile.name, requestFile)

                // 5. Enviar imagen al servidor
                val response = RetrofitClient.api.detectPlate(body)

                if (response.isSuccessful) {
                    val result = response.body()
                    val box = result?.box ?: emptyList()
                    val success = result?.success ?: false

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "✅ Placa detectada: ${if (success) "Sí" else "No"}", Toast.LENGTH_SHORT).show()
                    }

                    Log.d("ServerResponse", "Box: $box")

                    withContext(Dispatchers.Main) {
                        extractPlateFromBoxAndOcr(context, resizedBitmap, box, originalUri, croppedUri)
                    }

                    // Guardar en Firestore
                    //saveScanRecord(originalUri, croppedUri, plate = "Desconocida", success = success)

                } else {
                    val errorMsg = response.errorBody()?.string()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "❌ Error en respuesta del servidor: $errorMsg", Toast.LENGTH_LONG).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "❌ Error al enviar imagen: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } finally {
                tempFile?.delete()
            }
        }
    }

    private fun extractPlateFromBoxAndOcr(
        context: Context,
        resizedBitmap: Bitmap,
        box: List<Float>,
        imageUri: Uri,
        croppedUri: Uri
    ) {
        if (box.size != 4) {
            Toast.makeText(context, "❌ Coordenadas de la caja inválidas", Toast.LENGTH_LONG).show()
            return
        }

        val (x, y, w, h) = box

        // Tratar las coordenadas como píxeles reales (no normalizados)
        val left = (x - w * 0.3f).toInt().coerceIn(0, resizedBitmap.width - 1)
        val top = (y - h * 0.2f).toInt().coerceIn(0, resizedBitmap.height - 1)
        val right = (x + w * 1.2f).toInt().coerceIn(0, resizedBitmap.width)
        val bottom = (y + h * 1.2f).toInt().coerceIn(0, resizedBitmap.height)


        val cropRect = Rect(left, top, right, bottom)

        val croppedBitmap = Bitmap.createBitmap(
            resizedBitmap,
            cropRect.left,
            cropRect.top,
            cropRect.width(),
            cropRect.height()
        )

        // Mejora del procesamiento: convertir a escala de grises + binarización simple
        val processedBitmap = Bitmap.createBitmap(croppedBitmap.width, croppedBitmap.height, Bitmap.Config.ARGB_8888)
        for (i in 0 until croppedBitmap.width) {
            for (j in 0 until croppedBitmap.height) {
                val pixel = croppedBitmap.getPixel(i, j)
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF
                val gray = (0.299 * r + 0.587 * g + 0.114 * b).toInt()
                val threshold = 120
                val bw = if (gray < threshold) 0 else 255
                processedBitmap.setPixel(i, j, (0xFF shl 24) or (bw shl 16) or (bw shl 8) or bw)
            }
        }

        val image = InputImage.fromBitmap(processedBitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val fullText = visionText.text
                Log.d("OCR", "Texto detectado: $fullText")

                val regex = Regex("""[A-Z0-9]{2,4}-[A-Z0-9]{1,3}-?[A-Z0-9]{1,2}""")
                val possiblePlates = visionText.textBlocks
                    .flatMap { it.lines }
                    .mapNotNull { line ->
                        regex.find(line.text)?.value
                    }

                val plateMatch = possiblePlates.firstOrNull() ?: "NoDetectada"

                Toast.makeText(context, "🔍 OCR: $plateMatch", Toast.LENGTH_SHORT).show()

                saveScanRecord(imageUri, croppedUri, plate = plateMatch, success = plateMatch != "NoDetectada")
            }
            .addOnFailureListener {
                Toast.makeText(context, "❌ Error OCR: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

}