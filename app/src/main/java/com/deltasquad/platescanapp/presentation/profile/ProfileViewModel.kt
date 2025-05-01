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
        syncProfile()
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
            repository.createUserProfileIfNotExists() // <- aquí estaba el error
            _profile.value = repository.getUserProfile()
            _isRefreshing.value = false
        }
    }


}
