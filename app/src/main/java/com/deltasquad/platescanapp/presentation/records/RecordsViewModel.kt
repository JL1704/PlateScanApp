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

    private val _allScans = MutableStateFlow<List<ScanRecord>>(emptyList())
    val allScans: StateFlow<List<ScanRecord>> = _allScans

    private val _filteredScans = MutableStateFlow<List<ScanRecord>>(emptyList())
    val filteredScans: StateFlow<List<ScanRecord>> = _filteredScans

    // Guarda todas las placas para filtrado
    private var allScan: List<ScanRecord> = emptyList()

    fun allScans() {
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
                _allScans.value = scans
            }
    }

    fun fetchAllScans() {
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
                allScan = scans
                _filteredScans.value = scans // Inicialmente sin filtro
            }
    }

    fun filterScans(query: String) {
        _filteredScans.value = if (query.isBlank()) {
            emptyList()
        } else {
            allScan.filter {
                it.plate.contains(query, ignoreCase = true)
            }
        }
    }
}