package com.deltasquad.platescanapp.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

/**
 * Fábrica personalizada para crear instancias de [AuthViewModel] con parámetros específicos.
 *
 * @param auth Instancia de FirebaseAuth a inyectar en el ViewModel.
 * @param context Contexto necesario para inicializar el cliente de autenticación de Google.
 */
class AuthViewModelFactory(
    private val auth: FirebaseAuth,
    private val context: Context
) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [AuthViewModel] con las dependencias necesarias.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia del ViewModel solicitado.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(auth, context) as T
    }
}