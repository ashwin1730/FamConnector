package com.ltimindtree.famconnector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_audits")
data class AiAuditEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileIdHash: String,
    val sightingId: Long,
    val score: Int,
    val createdAt: Long
)
