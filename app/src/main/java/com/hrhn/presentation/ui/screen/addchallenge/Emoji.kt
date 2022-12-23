package com.hrhn.presentation.ui.screen.addchallenge

data class Emoji(
    val isChecked: Boolean = false,
    val label: String,
    val color: String
)

fun getEmojis() = listOf(
    Emoji(label = ":D", color = "#FFBBAC"),
    Emoji(label = ":3", color = "#FED977"),
    Emoji(label = ":P", color = "#ACDE80"),
    Emoji(label = ":/", color = "#B0D9FF"),
    Emoji(label = ":(", color = "#97B4FE"),
    Emoji(label = ":'(", color = "#E1BAFF")
)