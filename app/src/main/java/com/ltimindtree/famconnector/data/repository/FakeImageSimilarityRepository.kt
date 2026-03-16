package com.ltimindtree.famconnector.data.repository

import com.ltimindtree.famconnector.data.local.dao.ProfileDao
import com.ltimindtree.famconnector.data.local.entity.ProfileStatus
import com.ltimindtree.famconnector.domain.model.SimilarityMatch
import com.ltimindtree.famconnector.domain.repository.ImageSimilarityRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.random.Random

class FakeImageSimilarityRepository @Inject constructor(
    private val profileDao: ProfileDao
) : ImageSimilarityRepository {

    override suspend fun compareAgainstActiveProfiles(sightingImageBytes: ByteArray): List<SimilarityMatch> {
        // In a real app, this would send bytes to an AI service.
        // For this demo, we'll fetch active "Missing" profiles and return mock scores.
        val profiles = profileDao.getAllProfiles().first()
        val missingProfiles = profiles.filter { it.status == ProfileStatus.Missing }

        return missingProfiles.map { profile ->
            val score = Random.nextInt(20, 95)
            SimilarityMatch(
                profileId = profile.id,
                score = score,
                explanation = generateMockExplanation(score, profile.name)
            )
        }.sortedByDescending { it.score }
    }

    private fun generateMockExplanation(score: Int, name: String): String {
        return when {
            score > 80 -> "High similarity in clothing color and hair length compared to $name's profile."
            score > 50 -> "Moderate similarity in build and height range."
            else -> "Low similarity; some overlap in background or secondary features."
        }
    }
}
