package com.ltimindtree.famconnector.data.local.dao

import androidx.room.*
import com.ltimindtree.famconnector.data.local.entity.SightingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SightingDao {
    @Query("SELECT * FROM sightings ORDER BY time DESC")
    fun getAllSightings(): Flow<List<SightingEntity>>

    @Query("SELECT * FROM sightings WHERE alertId = :alertId ORDER BY time DESC")
    fun getSightingsForAlert(alertId: Long): Flow<List<SightingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSighting(sighting: SightingEntity): Long
}
