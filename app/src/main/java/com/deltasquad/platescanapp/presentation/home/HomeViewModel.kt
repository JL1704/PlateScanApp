package com.deltasquad.platescanapp.presentation.home

import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.model.ScanRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _latestScans = MutableStateFlow<List<ScanRecord>>(emptyList())
    val latestScans: StateFlow<List<ScanRecord>> = _latestScans

    private val _filteredScans = MutableStateFlow<List<ScanRecord>>(emptyList())
    val filteredScans: StateFlow<List<ScanRecord>> = _filteredScans

    // Guarda todas las placas para filtrado
    private var allScans: List<ScanRecord> = emptyList()

    fun fetchLatestScans() {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("scans")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .addOnSuccessListener { result ->
                val scans = result.mapNotNull { doc ->
                    doc.toObject(ScanRecord::class.java)?.copy(id = doc.id)
                }
                _latestScans.value = scans
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
                allScans = scans
                _filteredScans.value = scans // Inicialmente sin filtro
            }
    }

    fun filterScans(query: String) {
        _filteredScans.value = if (query.isBlank()) {
            emptyList()
        } else {
            allScans.filter {
                it.plate.contains(query, ignoreCase = true)
            }
        }
    }

}

