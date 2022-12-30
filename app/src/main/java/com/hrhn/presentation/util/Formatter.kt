package com.hrhn.presentation.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object Formatter {
    private val dateTimeFormatter: DateTimeFormatter =
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

    fun getTimeString(hourOfDay: Int, minute: Int): String =
        dateTimeFormatter.format(LocalTime.of(hourOfDay, minute))
}
