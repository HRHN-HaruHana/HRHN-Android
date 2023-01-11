package com.hrhn.data.mapper

import com.hrhn.data.entity.ChallengeEntity
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.model.Emoji

fun ChallengeEntity.toModel(): Challenge {
    return Challenge(
        id = id,
        date = date,
        content = content,
        emoji = emoji?.let { Emoji.valueOf(it) }
    )
}

fun Challenge.toEntity(): ChallengeEntity {
    return ChallengeEntity(
        id = id,
        date = date,
        content = content,
        emoji = emoji?.name
    )
}