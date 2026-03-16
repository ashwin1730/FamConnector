package com.ltimindtree.famconnector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AlertState {
    Open, UnderReview, Resolved
}

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long,
    val lastSeenLat: Double,
    val lastSeenLng: Double,
    val lastSeenTime: Long,
    val clothingDescription: String,
    val description: String,
    val state: AlertState,
    val createdAt: Long,
    val updatedAt: Long,
    val expiresAt: Long?
)
