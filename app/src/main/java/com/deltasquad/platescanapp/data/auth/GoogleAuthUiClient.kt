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

/**
 * Cliente personalizado que gestiona el flujo de autenticación con Google One Tap y Firebase Authentication.
 *
 * @param context Contexto de la aplicación o actividad.
 */
class GoogleAuthUiClient(
    private val context: Context
) {
    private val oneTapClient: SignInClient = Identity.getSignInClient(context)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Configuración de la solicitud de inicio de sesión con Google
    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("814636271181-kkc7u6ndrfrlsprnvajtdddi2v8u67go.apps.googleusercontent.com") // ID de cliente web desde Firebase Console
                .setFilterByAuthorizedAccounts(false) // Permite seleccionar cualquier cuenta de Google
                .build()
        )
        .setAutoSelectEnabled(true) // Selección automática si hay una sola cuenta
        .build()

    /**
     * Inicia el proceso de inicio de sesión con Google One Tap.
     *
     * @param activity Actividad desde la cual se lanza la autenticación.
     * @param launcher Función de lanzamiento del intentSender proporcionado por Google.
     */
    fun signIn(activity: Activity, launcher: (IntentSender) -> Unit) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                launcher(result.pendingIntent.intentSender) // Lanza el intent para mostrar el diálogo One Tap
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    /**
     * Procesa el resultado de la autenticación y lo vincula con Firebase.
     *
     * @param data Intent recibido tras el login.
     * @param onResult Callback que indica si la autenticación fue exitosa.
     */
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

    /**
     * Cierra la sesión del usuario en One Tap. También puede ejecutar una acción al finalizar.
     *
     * @param onComplete Acción opcional a ejecutar después de cerrar sesión.
     */
    fun signOut(onComplete: (() -> Unit)? = null) {
        oneTapClient.signOut()
            .addOnCompleteListener {
                onComplete?.invoke()
            }
    }
}
