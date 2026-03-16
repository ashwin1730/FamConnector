package com.ltimindtree.famconnector.data.local.dao

import androidx.room.*
import com.ltimindtree.famconnector.data.local.entity.AlertEntity
import com.ltimindtree.famconnector.data.local.entity.AlertState
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts")
    fun getAllAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE state = :state")
    fun getAlertsByState(state: AlertState): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE id = :id")
    suspend fun getAlertById(id: Long): AlertEntity?

    @Query("SELECT * FROM alerts WHERE profileId = :profileId")
    fun getAlertsForProfile(profileId: Long): Flow<List<AlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: AlertEntity): Long

    @Update
    suspend fun updateAlert(alert: AlertEntity)
}
