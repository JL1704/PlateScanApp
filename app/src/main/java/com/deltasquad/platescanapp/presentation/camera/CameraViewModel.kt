package com.deltasquad.platescanapp.presentation.camera

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.lifecycle.AndroidViewModel
import com.deltasquad.platescanapp.data.api.RetrofitClient
import com.deltasquad.platescanapp.data.api.RetrofitClient.api
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
                    Toast.makeText(getApplication(), "Scan guardado en Firestore", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.e("Firestore", "Error al guardar el escaneo", it)
                    Toast.makeText(getApplication(), "Error al guardar en Firestore: ${it.message}", Toast.LENGTH_LONG).show()
                }
/*
                .addOnSuccessListener {
                    Log.d("Firestore", "Scan guardado exitosamente")
                }
                .addOnFailureListener {
                    Log.e("Firestore", "Error al guardar el escaneo", it)
                }

 */
        }
    }

    fun sendCroppedImageToServer(context: Context, originalUri: Uri, croppedUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = context.contentResolver
            var tempFile: File? = null

            try {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "📤 Enviando imagen al servidor...", Toast.LENGTH_SHORT).show()
                }

                // Verifica si la imagen se abre correctamente
                val inputStream = contentResolver.openInputStream(originalUri)
                if (inputStream == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "❌ No se pudo abrir el InputStream para la imagen.", Toast.LENGTH_LONG).show()
                    }
                    return@launch
                }

                // Crear un archivo temporal para enviar la imagen
                tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
                inputStream.use { input -> tempFile.outputStream().use { output -> input.copyTo(output) } }

                val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", tempFile.name, requestFile)

                val response = RetrofitClient.api.detectPlate(body)

                if (response.isSuccessful) {
                    val result = response.body()
                    val plate = result?.plate ?: "Desconocida"
                    val success = result?.success ?: false

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Placa detectada: $plate", Toast.LENGTH_LONG).show()
                    }

                    // Guardar en Firestore con datos reales
                    saveScanRecord(originalUri, croppedUri, plate, success)
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


}

/*
    @SuppressLint("MissingPermission")
    fun saveScanRecord(imageUri: Uri, croppedUri: Uri) {
        val user = auth.currentUser ?: return
        val dateStr = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            val scanData = ScanRecord(
                plate = "AAA-123-A", // luego reemplazar con modelo
                image = imageUri.toString(),
                croppedImage = croppedUri.toString(),
                date = dateStr,
                location = "${location?.latitude ?: "unknown"}, ${location?.longitude ?: "unknown"}",
                state = "success", // o "error", según el modelo
                user = user.uid
            )

            db.collection("users")
                .document(user.uid)
                .collection("scans")
                .add(scanData)
                .addOnSuccessListener {
                    Log.d("Firestore", "Scan guardado exitosamente")
                }
                .addOnFailureListener {
                    Log.e("Firestore", "Error al guardar el escaneo", it)
                }
        }
    }

 */

/*
    fun sendCroppedImageToServer(context: Context, croppedUri: Uri) {
        Log.d("Upload", "Enviando imagen al servidor: $croppedUri")

        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = context.contentResolver
            var tempFile: File? = null

            try {
                // Crear archivo temporal desde el Uri
                val inputStream = contentResolver.openInputStream(croppedUri)
                tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
                inputStream?.use { input -> tempFile.outputStream().use { output -> input.copyTo(output) } }

                // Crear Multipart
                val requestFile = tempFile
                    .asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", tempFile.name, requestFile)

                // Enviar al servidor
                val response = RetrofitClient.api.detectPlate(body)

                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("API_RESPONSE", "✅ Placa: ${result?.plate}, Success: ${result?.success}")

                    // Aquí puedes comunicarlo al ViewModel o actualizar UI
                    // withContext(Dispatchers.Main) { viewModel.onPlateDetected(result?.plate) }

                } else {
                    val errorMsg = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "❌ Error en respuesta: $errorMsg")
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "❌ Error al enviar la imagen", e)

            } finally {
                tempFile?.delete()
            }
        }
    }

 */

