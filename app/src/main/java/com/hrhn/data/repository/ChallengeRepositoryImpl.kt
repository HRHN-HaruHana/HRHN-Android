package com.hrhn.data.repository

import androidx.paging.PagingData
import com.hrhn.data.local.source.ChallengeDataSource
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeRepositoryImpl @Inject constructor(
    private val challengeDataSource: ChallengeDataSource
) : ChallengeRepository {
    override val challengesFlow: Flow<PagingData<Challenge>>
        get() = challengeDataSource.challengesFlow

    override fun insertChallenge(challenge: Challenge): Result<Unit> {
        return runCatching { challengeDataSource.insertChallenge(challenge) }
    }

    override fun getChallengesWithPeriod(
        from: LocalDateTime,
        to: LocalDateTime
    ): Flow<List<Challenge>> {
        return challengeDataSource.getChallengesWithPeriod(from, to)
    }

    override suspend fun getLastChallenge(): Result<Challenge?> {
        return runCatching { challengeDataSource.getLastChallenge() }
    }

    override fun updateChallenge(challenge: Challenge): Result<Unit> {
        return runCatching { challengeDataSource.updateChallenge(challenge) }
    }

    override fun deleteChallenge(id: Long): Result<Unit> {
        return runCatching { challengeDataSource.deleteChallenge(id) }
    }
}