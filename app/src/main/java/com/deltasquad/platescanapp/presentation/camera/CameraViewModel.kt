package com.deltasquad.platescanapp.presentation.camera

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.lifecycle.AndroidViewModel
import com.deltasquad.platescanapp.data.model.ScanRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

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

}

