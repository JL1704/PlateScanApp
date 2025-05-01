package com.deltasquad.platescanapp.presentation.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import com.deltasquad.platescanapp.data.auth.GoogleAuthUiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val auth: FirebaseAuth,
    context: Context
) : ViewModel() {

    private val googleAuthUiClient = GoogleAuthUiClient(context)

    private val _isAuthenticated = MutableStateFlow(auth.currentUser != null)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun refreshAuthState() {
        _isAuthenticated.value = auth.currentUser != null
    }

    fun signInWithGoogle(activity: Activity, onIntentReady: (IntentSenderRequest) -> Unit) {
        googleAuthUiClient.signIn(activity) { intentSender ->
            val request = IntentSenderRequest.Builder(intentSender).build()
            onIntentReady(request)
        }
    }

    fun handleSignInResult(data: Intent?, onSuccess: () -> Unit, onFailure: () -> Unit) {
        googleAuthUiClient.handleSignInResult(data) { isSuccessful ->
            if (isSuccessful) {
                refreshAuthState()
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    fun signOut() {
        googleAuthUiClient.signOut {
            auth.signOut()
            _isAuthenticated.value = false
        }
    }

    fun getCurrentUser() = auth.currentUser
}
