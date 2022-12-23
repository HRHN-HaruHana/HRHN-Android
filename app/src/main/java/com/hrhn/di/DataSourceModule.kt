package com.hrhn.di

import com.hrhn.data.local.source.ChallengeDataSource
import com.hrhn.data.local.source.ChallengeDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun bindChallengeDataSource(challengeDataSource: ChallengeDataSourceImpl): ChallengeDataSource
}