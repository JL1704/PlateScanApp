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

/**
 * ViewModel responsable de manejar el estado de autenticación del usuario y coordinar
 * el flujo de autenticación con Google y Firebase.
 *
 * @param auth Instancia de FirebaseAuth para verificar el estado de autenticación.
 * @param context Contexto necesario para inicializar el cliente de autenticación de Google.
 */
class AuthViewModel(
    private val auth: FirebaseAuth,
    context: Context
) : ViewModel() {

    // Cliente personalizado para autenticación con Google One Tap
    private val googleAuthUiClient = GoogleAuthUiClient(context)

    // Estado interno del flujo de autenticación (true si el usuario está autenticado)
    private val _isAuthenticated = MutableStateFlow(auth.currentUser != null)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    /**
     * Refresca el estado de autenticación verificando si hay un usuario autenticado actualmente.
     */
    fun refreshAuthState() {
        _isAuthenticated.value = auth.currentUser != null
    }

    /**
     * Inicia el proceso de autenticación con Google One Tap.
     *
     * @param activity Actividad actual que lanza el intent.
     * @param onIntentReady Callback que recibe el IntentSenderRequest listo para ser lanzado.
     */
    fun signInWithGoogle(activity: Activity, onIntentReady: (IntentSenderRequest) -> Unit) {
        googleAuthUiClient.signIn(activity) { intentSender ->
            val request = IntentSenderRequest.Builder(intentSender).build()
            onIntentReady(request)
        }
    }

    /**
     * Maneja el resultado del intent de inicio de sesión con Google.
     *
     * @param data Intent recibido desde el launcher.
     * @param onSuccess Callback a ejecutar si la autenticación fue exitosa.
     * @param onFailure Callback a ejecutar si la autenticación falló.
     */
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

    /**
     * Cierra la sesión del usuario tanto en Google como en Firebase.
     * Actualiza el estado de autenticación.
     */
    fun signOut() {
        googleAuthUiClient.signOut {
            auth.signOut()
            _isAuthenticated.value = false
        }
    }

    /**
     * Devuelve el usuario actualmente autenticado, o `null` si no hay ninguno.
     */
    fun getCurrentUser() = auth.currentUser
}