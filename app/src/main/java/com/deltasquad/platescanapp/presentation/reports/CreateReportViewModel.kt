package com.deltasquad.platescanapp.presentation.reports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.model.PlateReport
import com.deltasquad.platescanapp.data.model.ScanRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class CreateReportViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userPlates = MutableStateFlow<List<ScanRecord>>(emptyList())
    val userPlates: StateFlow<List<ScanRecord>> = _userPlates

    // Estado del formulario
    var selectedPlate = mutableStateOf("")
        private set

    var selectedScanId = mutableStateOf("")
        private set

    var reportType = mutableStateOf("")
    var description = mutableStateOf("")
    var isSaving = mutableStateOf(false)

    fun onPlateSelected(plate: String, scanId: String) {
        selectedPlate.value = plate
        selectedScanId.value = scanId
    }

    fun onReportTypeChanged(value: String) {
        reportType.value = value
    }

    fun onDescriptionChanged(value: String) {
        description.value = value
    }

    fun resetForm() {
        selectedPlate.value = ""
        selectedScanId.value = ""
        reportType.value = ""
        description.value = ""
    }

    fun createReport(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val user = auth.currentUser ?: return onError(Exception("Usuario no autenticado"))
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val report = PlateReport(
            scanRecordId = selectedScanId.value,
            plate = selectedPlate.value,
            reportType = reportType.value,
            description = description.value,
            dateReported = currentDate,
            reporterId = user.uid,
            reporterName = user.displayName ?: ""
        )

        isSaving.value = true
        db.collection("users")
            .document(user.uid)
            .collection("reports")
            .add(report)
            .addOnSuccessListener {
                isSaving.value = false
                resetForm()
                onSuccess()
            }
            .addOnFailureListener {
                isSaving.value = false
                onError(it)
            }
    }

    fun fetchUserPlates() {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("scans")
            .get()
            .addOnSuccessListener { result ->
                val scans = result.mapNotNull { doc ->
                    doc.toObject(ScanRecord::class.java)?.copy(id = doc.id)
                }
                _userPlates.value = scans
            }
    }
}
