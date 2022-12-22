package com.hrhn.presentation.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("android:date")
fun TextView.setDateString(date: LocalDateTime) {
    this.text = date.format(DateTimeFormatter.ofPattern("MM/dd"))
}