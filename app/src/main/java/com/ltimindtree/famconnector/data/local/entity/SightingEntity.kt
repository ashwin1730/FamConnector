package com.ltimindtree.famconnector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sightings")
data class SightingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val alertId: Long?,
    val lat: Double,
    val lng: Double,
    val time: Long,
    val note: String,
    val photoUri: String,
    val aiScore: Int?,
    val aiExplanation: String?,
    val createdAt: Long
)
