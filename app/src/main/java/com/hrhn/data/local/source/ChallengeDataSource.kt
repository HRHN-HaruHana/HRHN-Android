package com.hrhn.data.local.source

import com.hrhn.domain.model.Challenge
import java.time.LocalDateTime

interface ChallengeDataSource {
    fun insertChallenge(challenge: Challenge)
    fun getChallenges(): List<Challenge>
    fun getChallengesWithPeriod(from: LocalDateTime, to: LocalDateTime): List<Challenge>
    fun updateChallenge(challenge: Challenge)
    fun deleteChallenge(id: Long)
}