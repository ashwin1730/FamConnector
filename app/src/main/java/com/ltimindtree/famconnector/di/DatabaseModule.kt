package com.ltimindtree.famconnector.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ltimindtree.famconnector.data.local.AppDatabase
import com.ltimindtree.famconnector.data.local.dao.AlertDao
import com.ltimindtree.famconnector.data.local.dao.ProfileDao
import com.ltimindtree.famconnector.data.local.dao.SightingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fam_connector_db"
        ).build()
    }

    @Provides
    fun provideProfileDao(database: AppDatabase): ProfileDao = database.profileDao()

    @Provides
    fun provideAlertDao(database: AppDatabase): AlertDao = database.alertDao()

    @Provides
    fun provideSightingDao(database: AppDatabase): SightingDao = database.sightingDao()
}
