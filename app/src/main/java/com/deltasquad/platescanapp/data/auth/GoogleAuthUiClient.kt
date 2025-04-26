package com.deltasquad.platescanapp.data.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthUiClient(
    private val context: Context
) {
    private val oneTapClient: SignInClient = Identity.getSignInClient(context)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("814636271181-kkc7u6ndrfrlsprnvajtdddi2v8u67go.apps.googleusercontent.com") // Desde Firebase > Configuración > ID de cliente web
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    fun signIn(activity: Activity, launcher: (IntentSender) -> Unit) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                launcher(result.pendingIntent.intentSender) // <-- usar intentSender
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }


    fun handleSignInResult(data: Intent?, onResult: (Boolean) -> Unit) {
        oneTapClient.getSignInCredentialFromIntent(data).let { credential ->
            val googleIdToken = credential.googleIdToken
            if (googleIdToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        onResult(task.isSuccessful)
                    }
            } else {
                onResult(false)
            }
        }
    }
}
