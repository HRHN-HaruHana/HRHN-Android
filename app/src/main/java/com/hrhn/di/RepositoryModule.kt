package com.hrhn.di

import com.hrhn.data.repository.ChallengeRepositoryImpl
import com.hrhn.domain.repository.ChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindChallengeRepository(challengeRepository: ChallengeRepositoryImpl): ChallengeRepository
}