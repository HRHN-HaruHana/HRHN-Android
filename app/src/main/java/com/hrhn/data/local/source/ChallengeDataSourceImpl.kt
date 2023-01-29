package com.hrhn.data.local.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hrhn.data.local.dao.ChallengeDao
import com.hrhn.data.mapper.toEntity
import com.hrhn.data.mapper.toModel
import com.hrhn.domain.model.Challenge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeDataSourceImpl @Inject constructor(
    private val challengeDao: ChallengeDao
) : ChallengeDataSource {
    override val challengesFlow: Flow<PagingData<Challenge>>
        get() = Pager(PagingConfig(10)) { challengeDao.getChallengesFlow() }
            .flow.map { it.map { it.toModel() } }

    override fun insertChallenge(challenge: Challenge) {
        challengeDao.insertChallenge(challenge.toEntity())
    }

    override suspend fun getLastChallenge(): Challenge? {
        return challengeDao.getLastChallenge()?.toModel()
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