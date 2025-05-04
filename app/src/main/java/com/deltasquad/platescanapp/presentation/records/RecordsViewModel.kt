package com.deltasquad.platescanapp.presentation.records

import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.model.ScanRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecordsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _latestScans = MutableStateFlow<List<ScanRecord>>(emptyList())
    val latestScans: StateFlow<List<ScanRecord>> = _latestScans

    fun fetchLatestScans() {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("scans")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val scans = result.mapNotNull { doc ->
                    doc.toObject(ScanRecord::class.java)?.copy(id = doc.id)
                }
                _latestScans.value = scans
            }
    }
}