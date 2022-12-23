package com.hrhn.domain.repository

import com.hrhn.domain.model.Challenge
import java.time.LocalDateTime

interface ChallengeRepository {
    fun insertChallenge(challenge: Challenge): Result<Unit>
    fun getChallenges(): Result<List<Challenge>>
    fun getChallengesWithPeriod(from: LocalDateTime, to: LocalDateTime): Result<List<Challenge>>
    fun getLastChallenge(): Result<Challenge?>
    fun updateChallenge(challenge: Challenge): Result<Unit>
    fun deleteChallenge(id: Long): Result<Unit>
    fun getPastChallenges(): List<Challenge>
    fun addChallenge(challenge: Challenge): Result<Long>
}