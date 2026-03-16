package com.ltimindtree.famconnector.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ltimindtree.famconnector.data.local.entity.AlertState
import com.ltimindtree.famconnector.data.local.entity.ProfileEntity
import com.ltimindtree.famconnector.data.local.entity.ProfileStatus
import com.ltimindtree.famconnector.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FamilyStats(
    val foundCount: Int = 0,
    val activeAlertsCount: Int = 0,
    val sightingsCount: Int = 0
)

@HiltViewModel
class MyFamilyViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val profiles: StateFlow<List<ProfileEntity>> = repository.getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats: StateFlow<FamilyStats> = combine(
        repository.getAllProfiles(),
        repository.getAllAlerts(),
        repository.getAllSightings()
    ) { profiles, alerts, sightings ->
        FamilyStats(
            foundCount = profiles.count { it.status == ProfileStatus.Resolved },
            activeAlertsCount = alerts.count { it.state != AlertState.Resolved },
            sightingsCount = sightings.size
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FamilyStats())

    fun deleteProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            repository.deleteProfile(profile)
        }
    }
}
