package com.deltasquad.platescanapp.presentation.details

import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.model.ScanRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _scanRecord = MutableStateFlow<ScanRecord?>(null)
    val scanRecord: StateFlow<ScanRecord?> = _scanRecord

    fun fetchScanById(scanId: String) {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("scans")
            .document(scanId)
            .get()
            .addOnSuccessListener { result ->
                val scan = result.toObject(ScanRecord::class.java)
                _scanRecord.value = scan
            }
    }

    fun deleteScanById(scanId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("scans")
            .document(scanId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateScanData(
        scanId: String,
        updatedData: Map<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("scans")
            .document(scanId)
            .update(updatedData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }


}
