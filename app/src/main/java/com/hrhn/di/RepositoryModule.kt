package com.hrhn.di

import com.hrhn.data.repository.ChallengeRepositoryImpl
import com.hrhn.data.repository.FakeStorageRepository
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.domain.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindChallengeRepository(challengeRepository: ChallengeRepositoryImpl): ChallengeRepository

    @Binds
    abstract fun bindStorageRepository(storageRepository: FakeStorageRepository): StorageRepository
}