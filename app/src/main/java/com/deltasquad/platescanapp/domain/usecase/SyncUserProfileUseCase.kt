package com.deltasquad.platescanapp.domain.usecase

import com.deltasquad.platescanapp.data.repository.ProfileRepository

class SyncUserProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke() {
        repository.createUserProfileIfNotExists()
    }
}
