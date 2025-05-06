package com.deltasquad.platescanapp.presentation.reports


import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.model.PlateReport
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReportsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _allReports = MutableStateFlow<List<PlateReport>>(emptyList())
    val allReports: StateFlow<List<PlateReport>> = _allReports

    fun fetchAllReports() {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("reports")
            .orderBy("dateReported", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val reports = result.mapNotNull { doc ->
                    doc.toObject(PlateReport::class.java)?.copy(id = doc.id)
                }
                _allReports.value = reports
            }
    }
}
