package com.ltimindtree.famconnector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ProfileStatus {
    Active, Missing, Resolved
}

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ageRange: String,
    val gender: String?,
    val heightRange: String?,
    val notes: String?,
    val photoUris: List<String>,
    val consentGiven: Boolean,
    val status: ProfileStatus,
    val createdAt: Long,
    val updatedAt: Long
)
