package com.deltasquad.platescanapp.presentation.detailsreport

import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.model.PlateReport
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailsReportViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _report = MutableStateFlow<PlateReport?>(null)
    val report: StateFlow<PlateReport?> = _report

    fun fetchReportById(reportId: String) {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("reports")
            .document(reportId)
            .get()
            .addOnSuccessListener { document ->
                val report = document.toObject(PlateReport::class.java)?.copy(id = document.id)
                _report.value = report
            }
    }
}
