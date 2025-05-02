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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        viewModelScope.launch {
            repository.createUserProfileIfNotExists() // Se asegura que existe una vez
            _profile.value = repository.getUserProfile()
        }
    }

    fun syncProfileManually() {
        viewModelScope.launch {
            repository.createUserProfileIfNotExists()
            _profile.value = repository.getUserProfile()
        }
    }

    private fun syncProfile() {
        viewModelScope.launch {
            repository.createUserProfileIfNotExists()
            _profile.value = repository.getUserProfile()
        }
    }

    fun refreshProfile() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _profile.value = repository.getUserProfile() // Solo obtener, sin crear ni actualizar
            _isRefreshing.value = false
        }
    }

    fun updateUserProfile(username: String, phone: String, imageUrl: String? = null, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateUserProfile(username, phone, imageUrl)
                _profile.value = repository.getUserProfile() // Actualiza el estado
                onResult(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

}
