package com.ltimindtree.famconnector.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ltimindtree.famconnector.data.local.dao.AlertDao
import com.ltimindtree.famconnector.data.local.dao.ProfileDao
import com.ltimindtree.famconnector.data.local.dao.SightingDao
import com.ltimindtree.famconnector.data.local.entity.AiAuditEntity
import com.ltimindtree.famconnector.data.local.entity.AlertEntity
import com.ltimindtree.famconnector.data.local.entity.ProfileEntity
import com.ltimindtree.famconnector.data.local.entity.SightingEntity

@Database(
    entities = [
        ProfileEntity::class,
        AlertEntity::class,
        SightingEntity::class,
        AiAuditEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun alertDao(): AlertDao
    abstract fun sightingDao(): SightingDao
}
