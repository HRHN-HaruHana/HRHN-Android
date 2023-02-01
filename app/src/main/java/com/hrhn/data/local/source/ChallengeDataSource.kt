package com.hrhn.data.local.source

import androidx.paging.PagingData
import com.hrhn.domain.model.Challenge
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ChallengeDataSource {
    val challengesFlow: Flow<PagingData<Challenge>>
    fun insertChallenge(challenge: Challenge)
    suspend fun getLastChallenge(): Challenge?
    fun getChallengesWithPeriod(from: LocalDateTime, to: LocalDateTime): Flow<List<Challenge>>
    fun updateChallenge(challenge: Challenge)
    fun deleteChallenge(id: Long)
}