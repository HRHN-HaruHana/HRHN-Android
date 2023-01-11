package com.hrhn.presentation.ui.screen.addchallenge

import com.hrhn.domain.model.Emoji

data class CheckableEmoji(
    val isChecked: Boolean = false,
    val emoji: Emoji
)