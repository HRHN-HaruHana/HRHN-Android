package com.hrhn.presentation.util

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("android:date")
fun TextView.setDateString(date: LocalDateTime) {
    this.text = date.format(DateTimeFormatter.ofPattern("MM/dd"))
}

@BindingAdapter("android:dateWithYear")
fun TextView.setDateWithYearString(date: LocalDateTime) {
    this.text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
}

@BindingAdapter("android:visibleIf")
fun View.setVisible(boolean: Boolean) {
    isVisible = boolean
}