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

        val currentData = snapshot.toObject(UserProfile::class.java)

        val newProfile = UserProfile(
            username = currentUser.displayName ?: currentData?.username ?: "Unnamed",
            email = currentUser.email ?: currentData?.email ?: "",
            phone = currentUser.phoneNumber ?: currentData?.phone ?: "",
            image = currentUser.photoUrl?.toString() ?: currentData?.image ?: ""
        )

        if (!snapshot.exists() || currentData == null) {
            docRef.set(newProfile).await()
        } else {
            docRef.update(
                mapOf(
                    "username" to newProfile.username,
                    "email" to newProfile.email,
                    "phone" to newProfile.phone,
                    "image" to newProfile.image
                )
            ).await()
        }
    }


    suspend fun getUserProfile(): UserProfile? {
        val currentUser = auth.currentUser ?: return null
        val snapshot = profilesCollection.document(currentUser.uid).get().await()
        return snapshot.toObject(UserProfile::class.java)
    }
}
