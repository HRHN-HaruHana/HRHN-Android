package com.hrhn.data.local.source

import com.hrhn.data.local.dao.ChallengeDao
import com.hrhn.data.mapper.toEntity
import com.hrhn.data.mapper.toModel
import com.hrhn.domain.model.Challenge
import java.time.LocalDateTime
import javax.inject.Inject

class ChallengeDataSourceImpl @Inject constructor(
    private val challengeDao: ChallengeDao
) : ChallengeDataSource {
    override fun insertChallenge(challenge: Challenge) {
        challengeDao.insertChallenge(challenge.toEntity())
    }

    override fun getChallenges(): List<Challenge> {
        return challengeDao.getChallenges().map { it.toModel() }
    }

    override fun getChallengesWithPeriod(from: LocalDateTime, to: LocalDateTime): List<Challenge> {
        return challengeDao.getChallengesWithPeriod(from, to).map { it.toModel() }
    }

    override fun updateChallenge(challenge: Challenge) {
        challengeDao.updateChallenge(challenge.toEntity())
    }

    override fun deleteChallenge(id: Long) {
        challengeDao.deleteChallenge(id)
    }
}