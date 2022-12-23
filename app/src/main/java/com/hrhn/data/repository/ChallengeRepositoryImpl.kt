package com.hrhn.data.repository

import com.hrhn.domain.model.Emoji
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository

class ChallengeRepositoryImpl : ChallengeRepository {
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