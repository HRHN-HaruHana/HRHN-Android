package com.hrhn.data.repository

import com.hrhn.data.local.source.ChallengeDataSource
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeRepositoryImpl @Inject constructor(
    private val challengeDataSource: ChallengeDataSource
) : ChallengeRepository {
    override fun insertChallenge(challenge: Challenge): Result<Unit> {
        return runCatching { challengeDataSource.insertChallenge(challenge) }
    }

    override fun getChallenges(): Result<List<Challenge>> {
        return runCatching { challengeDataSource.getChallenges() }
    }

    override fun getChallengesWithPeriod(
        from: LocalDateTime,
        to: LocalDateTime
    ): Result<List<Challenge>> {
        return runCatching { challengeDataSource.getChallengesWithPeriod(from, to) }
    }

    override fun getLastChallenge(): Result<Challenge?> {
        return runCatching { challengeDataSource.getChallenges().getOrNull(0) }
    }

    override fun updateChallenge(challenge: Challenge): Result<Unit> {
        return runCatching { challengeDataSource.updateChallenge(challenge) }
    }

    override fun deleteChallenge(id: Long): Result<Unit> {
        return runCatching { challengeDataSource.deleteChallenge(id) }
    }
}