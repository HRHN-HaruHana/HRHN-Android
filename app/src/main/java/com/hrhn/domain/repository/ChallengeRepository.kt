package com.hrhn.domain.repository

import androidx.paging.PagingData
import com.hrhn.domain.model.Challenge
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ChallengeRepository {
    val challengesFlow: Flow<PagingData<Challenge>>
    fun insertChallenge(challenge: Challenge): Result<Unit>
    fun getChallengesWithPeriod(from: LocalDateTime, to: LocalDateTime): Flow<List<Challenge>>
    suspend fun getLastChallenge(): Result<Challenge?>
    fun updateChallenge(challenge: Challenge): Result<Unit>
    fun deleteChallenge(id: Long): Result<Unit>
}