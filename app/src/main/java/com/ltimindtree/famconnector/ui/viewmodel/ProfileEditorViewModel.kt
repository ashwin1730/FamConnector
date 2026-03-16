package com.ltimindtree.famconnector.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ltimindtree.famconnector.data.local.entity.AlertEntity
import com.ltimindtree.famconnector.data.local.entity.AlertState
import com.ltimindtree.famconnector.data.local.entity.ProfileEntity
import com.ltimindtree.famconnector.data.local.entity.ProfileStatus
import com.ltimindtree.famconnector.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditorViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val profileId: Long = savedStateHandle.get<Long>("profileId") ?: -1L

    private val _profile = MutableStateFlow<ProfileEntity?>(null)
    val profile: StateFlow<ProfileEntity?> = _profile

    init {
        if (profileId != -1L) {
            viewModelScope.launch {
                _profile.value = repository.getProfileById(profileId)
            }
        }
    }

    fun saveProfile(
        name: String,
        ageRange: String,
        gender: String?,
        heightRange: String?,
        notes: String?,
        photoUris: List<String>,
        consentGiven: Boolean
    ) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val newProfile = ProfileEntity(
                id = if (profileId == -1L) 0 else profileId,
                name = name,
                ageRange = ageRange,
                gender = gender,
                heightRange = heightRange,
                notes = notes,
                photoUris = photoUris,
                consentGiven = consentGiven,
                status = _profile.value?.status ?: ProfileStatus.Active,
                createdAt = _profile.value?.createdAt ?: now,
                updatedAt = now
            )
            repository.insertProfile(newProfile)
        }
    }

    fun publishAlert(
        clothingDescription: String,
        description: String,
        lastSeenLat: Double = 0.0,
        lastSeenLng: Double = 0.0
    ) {
        viewModelScope.launch {
            val currentProfile = _profile.value ?: return@launch
            
            // 1. Update Profile Status to Missing
            repository.updateProfile(currentProfile.copy(status = ProfileStatus.Missing, updatedAt = System.currentTimeMillis()))
            
            // 2. Insert Alert record
            val now = System.currentTimeMillis()
            val alert = AlertEntity(
                profileId = currentProfile.id,
                lastSeenLat = lastSeenLat,
                lastSeenLng = lastSeenLng,
                lastSeenTime = now,
                clothingDescription = clothingDescription,
                description = description,
                state = AlertState.Open,
                createdAt = now,
                updatedAt = now,
                expiresAt = now + (7 * 24 * 60 * 60 * 1000) // 1 week default
            )
            repository.insertAlert(alert)
        }
    }
}
