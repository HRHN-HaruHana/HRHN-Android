package com.hrhn.domain.repository

import com.hrhn.domain.model.Challenge

interface ChallengeRepository {
    fun getPastChallenges(): List<Challenge>
    fun addChallenge(challenge: Challenge): Result<Long>
}