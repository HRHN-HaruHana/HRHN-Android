package com.hrhn.data.repository

import com.hrhn.R
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository

class ChallengeRepositoryImpl : ChallengeRepository {
    override fun getPastChallenges(): List<Challenge> {
        return listOf(
            Challenge(content = "영단어 100개 외우기", emoji = ":D", color = R.color.red_01),
            Challenge(content = "영단어 50개 외우기", emoji = ":D", color = R.color.purple_01),
            Challenge(content = "영단어 70개 외우기", emoji = ":D", color = R.color.green_01),
            Challenge(content = "영단어 80개 외우기", emoji = ":D", color = R.color.skyblue_01),
            Challenge(content = "영단어 90개 외우기", emoji = ":D", color = R.color.yellow_01),
            Challenge(content = "영단어 10개 외우기", emoji = ":D", color = R.color.skyblue_01),
            Challenge(content = "영단어 20개 외우기", emoji = ":D", color = R.color.yellow_01),
            Challenge(content = "영단어 100개 외우기", emoji = ":D", color = R.color.red_01),
            Challenge(content = "영단어 100개 외우기", emoji = ":D", color = R.color.green_01),
            Challenge(content = "영단어 100개 외우기", emoji = ":D", color = R.color.blue_01)
        )
    }
}