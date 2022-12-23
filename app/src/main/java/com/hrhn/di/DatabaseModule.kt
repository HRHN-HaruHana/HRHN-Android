package com.hrhn.di

import android.content.Context
import androidx.room.Room
import com.hrhn.R
import com.hrhn.data.local.dao.ChallengeDao
import com.hrhn.data.local.db.HRHNDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideHRHNDatabase(
        @ApplicationContext context: Context
    ): HRHNDatabase {
        return Room.databaseBuilder(
            context,
            HRHNDatabase::class.java,
            context.getString(R.string.app_database_name)
        ).build()
    }

    @Singleton
    @Provides
    fun provideChallengeDao(database: HRHNDatabase): ChallengeDao {
        return database.challengeDao()
    }
}