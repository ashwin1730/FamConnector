package com.ltimindtree.famconnector.data.repository

import com.ltimindtree.famconnector.data.local.dao.AlertDao
import com.ltimindtree.famconnector.data.local.dao.ProfileDao
import com.ltimindtree.famconnector.data.local.dao.SightingDao
import com.ltimindtree.famconnector.data.local.entity.AlertEntity
import com.ltimindtree.famconnector.data.local.entity.AlertState
import com.ltimindtree.famconnector.data.local.entity.ProfileEntity
import com.ltimindtree.famconnector.data.local.entity.ProfileStatus
import com.ltimindtree.famconnector.data.local.entity.SightingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val profileDao: ProfileDao,
    private val alertDao: AlertDao,
    private val sightingDao: SightingDao
) {
    fun getAllProfiles(): Flow<List<ProfileEntity>> = profileDao.getAllProfiles()
    suspend fun getProfileById(id: Long) = profileDao.getProfileById(id)
    suspend fun insertProfile(profile: ProfileEntity) = profileDao.insertProfile(profile)
    suspend fun updateProfile(profile: ProfileEntity) = profileDao.updateProfile(profile)
    suspend fun deleteProfile(profile: ProfileEntity) = profileDao.deleteProfile(profile)

    fun getAllAlerts(): Flow<List<AlertEntity>> = alertDao.getAllAlerts()
    suspend fun getAlertById(id: Long) = alertDao.getAlertById(id)
    suspend fun insertAlert(alert: AlertEntity) = alertDao.insertAlert(alert)
    suspend fun updateAlert(alert: AlertEntity) = alertDao.updateAlert(alert)

    fun getAllSightings(): Flow<List<SightingEntity>> = sightingDao.getAllSightings()
    suspend fun insertSighting(sighting: SightingEntity) = sightingDao.insertSighting(sighting)

    suspend fun resolveCase(profileId: Long, alertId: Long) {
        val profile = profileDao.getProfileById(profileId)
        val alert = alertDao.getAlertById(alertId)
        
        if (profile != null) {
            profileDao.updateProfile(profile.copy(status = ProfileStatus.Resolved, updatedAt = System.currentTimeMillis()))
        }
        if (alert != null) {
            alertDao.updateAlert(alert.copy(state = AlertState.Resolved, updatedAt = System.currentTimeMillis()))
        }
    }
}
