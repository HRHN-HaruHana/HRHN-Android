package com.hrhn.domain.model

import androidx.annotation.ColorRes
import java.time.LocalDateTime

data class Challenge(
    val date: LocalDateTime = LocalDateTime.now(),
    val content: String = "",
    val emoji: String? = null,
    @ColorRes val color: Int? = null
)