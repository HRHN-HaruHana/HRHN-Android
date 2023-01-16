package com.hrhn.presentation.ui.view

import com.hrhn.domain.model.Emoji

data class CheckableEmoji(
    val isChecked: Boolean = false,
    val emoji: Emoji
)