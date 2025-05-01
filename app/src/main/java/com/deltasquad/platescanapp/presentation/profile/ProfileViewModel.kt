package com.deltasquad.platescanapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deltasquad.platescanapp.data.model.UserProfile
import com.deltasquad.platescanapp.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    init {
        syncProfile()
    }

    private fun syncProfile() {
        viewModelScope.launch {
            repository.createUserProfileIfNotExists()
            _profile.value = repository.getUserProfile()
        }
    }
}
