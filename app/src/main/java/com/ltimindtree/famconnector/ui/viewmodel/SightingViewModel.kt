package com.ltimindtree.famconnector.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ltimindtree.famconnector.data.local.entity.SightingEntity
import com.ltimindtree.famconnector.data.repository.MainRepository
import com.ltimindtree.famconnector.domain.model.SimilarityMatch
import com.ltimindtree.famconnector.domain.repository.ImageSimilarityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SightingViewModel @Inject constructor(
    private val repository: MainRepository,
    private val similarityRepository: ImageSimilarityRepository
) : ViewModel() {

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _matches = MutableStateFlow<List<SimilarityMatch>>(emptyList())
    val matches: StateFlow<List<SimilarityMatch>> = _matches

    fun submitSighting(
        lat: Double,
        lng: Double,
        note: String,
        photoUri: String,
        photoBytes: ByteArray
    ) {
        viewModelScope.launch {
            _isSubmitting.value = true
            
            // 1. Get AI matches
            val aiMatches = similarityRepository.compareAgainstActiveProfiles(photoBytes)
            _matches.value = aiMatches

            // 2. Save sighting (for simplicity, we link to the top match if score is high, or leave null)
            val topMatch = aiMatches.firstOrNull()
            val alertId = if (topMatch != null && topMatch.score > 50) {
                // In a real app, we'd find the alertId for this profileId
                null // Placeholder
            } else null

            val sighting = SightingEntity(
                alertId = alertId,
                lat = lat,
                lng = lng,
                time = System.currentTimeMillis(),
                note = note,
                photoUri = photoUri,
                aiScore = topMatch?.score,
                aiExplanation = topMatch?.explanation,
                createdAt = System.currentTimeMillis()
            )
            repository.insertSighting(sighting)
            
            _isSubmitting.value = false
        }
    }
}
