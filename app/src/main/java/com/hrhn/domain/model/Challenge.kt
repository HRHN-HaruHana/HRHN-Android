package com.hrhn.domain.model

import java.time.LocalDateTime

data class Challenge(
    val id: Long = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val content: String = "",
    val emoji: Emoji? = null
)