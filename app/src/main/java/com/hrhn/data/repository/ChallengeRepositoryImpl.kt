package com.hrhn.data.repository

import com.hrhn.data.local.source.ChallengeDataSource
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.model.Emoji
import com.hrhn.domain.repository.ChallengeRepository
import java.time.LocalDateTime
import javax.inject.Inject

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
        return kotlin.runCatching { challengeDataSource.deleteChallenge(id) }
    }

    override fun getPastChallenges(): List<Challenge> {
        return listOf(
            Challenge(content = "영단어 100개 외우기", emoji = Emoji.BLUE),
            Challenge(content = "영단어 50개 외우기", emoji = Emoji.GREEN),
            Challenge(content = "영단어 70개 외우기", emoji = Emoji.YELLOW),
            Challenge(content = "영단어 80개 외우기", emoji = Emoji.RED),
            Challenge(content = "영단어 90개 외우기", emoji = Emoji.SKYBLUE),
            Challenge(content = "영단어 10개 외우기", emoji = Emoji.RED),
            Challenge(content = "영단어 20개 외우기", emoji = Emoji.BLUE),
            Challenge(content = "영단어 100개 외우기", emoji = Emoji.RED),
            Challenge(content = "영단어 100개 외우기", emoji = Emoji.RED),
            Challenge(content = "영단어 100개 외우기", emoji = Emoji.PURPLE)
        )
    }

    override fun addChallenge(challenge: Challenge): Result<Long> {
        return Result.success(1)
    }
}