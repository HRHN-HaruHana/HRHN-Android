package com.hrhn.data.local.source

import com.hrhn.domain.model.Challenge

interface ChallengeDataSource {
    fun insertChallenge(challenge: Challenge)
    fun getChallenges(): List<Challenge>
    fun updateChallenge(challenge: Challenge)
    fun deleteChallenge(id: Long)
}