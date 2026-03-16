package com.ltimindtree.famconnector.domain.repository

import com.ltimindtree.famconnector.domain.model.SimilarityMatch

interface ImageSimilarityRepository {
    suspend fun compareAgainstActiveProfiles(sightingImageBytes: ByteArray): List<SimilarityMatch>
}
