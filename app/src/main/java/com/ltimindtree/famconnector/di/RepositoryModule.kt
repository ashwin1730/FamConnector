package com.ltimindtree.famconnector.di

import com.ltimindtree.famconnector.data.repository.FakeImageSimilarityRepository
import com.ltimindtree.famconnector.domain.repository.ImageSimilarityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindImageSimilarityRepository(
        fakeImageSimilarityRepository: FakeImageSimilarityRepository
    ): ImageSimilarityRepository
}
