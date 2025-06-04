package com.deltasquad.platescanapp.data.repository

import com.deltasquad.platescanapp.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de gestionar las operaciones relacionadas con el perfil del usuario
 * en Firebase Firestore y la autenticación con FirebaseAuth.
 *
 * @param firestore Instancia de FirebaseFirestore utilizada para acceder a la colección de perfiles.
 * @param auth Instancia de FirebaseAuth utilizada para obtener el usuario autenticado actual.
 */
class ProfileRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    // Referencia a la colección "Profiles" en Firestore
    private val profilesCollection = firestore.collection("Profiles")

    /**
     * Crea un perfil de usuario en Firestore si aún no existe.
     * Se utiliza el UID del usuario autenticado como ID del documento.
     */
    suspend fun createUserProfileIfNotExists() {
        val currentUser = auth.currentUser ?: return
        val docRef = profilesCollection.document(currentUser.uid)
        val snapshot = docRef.get().await()

        if (!snapshot.exists()) {
            val newProfile = UserProfile(
                username = currentUser.displayName ?: "Unnamed",
                email = currentUser.email ?: "",
                phone = currentUser.phoneNumber ?: "",
                image = currentUser.photoUrl?.toString() ?: ""
            )
            docRef.set(newProfile).await()
        }
    }

    /**
     * Recupera el perfil del usuario actual desde Firestore.
     *
     * @return [UserProfile] si existe el perfil, o `null` si el usuario no está autenticado o el perfil no existe.
     */
    suspend fun getUserProfile(): UserProfile? {
        val currentUser = auth.currentUser ?: return null
        val snapshot = profilesCollection.document(currentUser.uid).get().await()
        return snapshot.toObject(UserProfile::class.java)
    }

    /**
     * Actualiza el perfil del usuario con los nuevos valores proporcionados.
     *
     * @param username Nuevo nombre de usuario.
     * @param phone Nuevo número de teléfono.
     * @param imageUrl Nueva URL de imagen de perfil (opcional).
     */
    suspend fun updateUserProfile(username: String, phone: String, imageUrl: String?) {
        val currentUser = auth.currentUser ?: return
        val userDocRef = profilesCollection.document(currentUser.uid)

        val updates = mutableMapOf<String, Any>(
            "username" to username,
            "phone" to phone
        )
        imageUrl?.let {
            updates["image"] = it
        }

        userDocRef.update(updates).await()
    }
}
