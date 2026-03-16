package com.ltimindtree.famconnector.domain.model

data class SimilarityMatch(
    val profileId: Long,
    val score: Int,
    val explanation: String
)
