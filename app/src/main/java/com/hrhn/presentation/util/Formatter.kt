package com.hrhn.presentation.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

object Formatter {
    fun getTimeString(hourOfDay: Int, minute: Int): String =
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(Locale.getDefault())
            .format(LocalTime.of(hourOfDay, minute))
}
