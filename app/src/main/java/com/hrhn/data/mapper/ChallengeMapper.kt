package com.hrhn.data.mapper

import com.hrhn.data.entity.ChallengeEntity
import com.hrhn.domain.model.Emoji
import com.hrhn.domain.model.Challenge
import java.sql.Date
import java.time.ZoneId

fun ChallengeEntity.toModel(): Challenge {
    return Challenge(
        id = id,
        date = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        content = content,
        emoji = emoji?.let { Emoji.valueOf(it) }
    )
}

fun Challenge.toEntity(): ChallengeEntity {
    return ChallengeEntity(
        id = id,
        date = Date.valueOf(date.toString()),
        content = content,
        emoji = emoji?.name
    )
}