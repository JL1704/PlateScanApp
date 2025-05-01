package com.deltasquad.platescanapp.data.repository

import com.deltasquad.platescanapp.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfileRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val profilesCollection = firestore.collection("Profiles")

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

    suspend fun getUserProfile(): UserProfile? {
        val currentUser = auth.currentUser ?: return null
        val snapshot = profilesCollection.document(currentUser.uid).get().await()
        return snapshot.toObject(UserProfile::class.java)
    }
}
