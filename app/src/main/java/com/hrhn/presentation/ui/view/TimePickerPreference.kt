package com.hrhn.presentation.ui.view

import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat.is24HourFormat
import android.util.AttributeSet
import android.widget.Button
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceViewHolder
import com.hrhn.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class TimePickerPreference constructor(
    context: Context, attrs: AttributeSet?
) : Preference(context, attrs) {

    private val sharedPreference by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    private lateinit var button: Button

    private val timeSetListener by lazy {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            sharedPreference.edit {
                putInt(context.getString(R.string.key_notification_hour), hourOfDay)
                putInt(context.getString(R.string.key_notification_minute), minute)
            }
            button.text = formatter.format(LocalTime.of(hourOfDay, minute))
        }
    }

    init {
        widgetLayoutResource = R.layout.preference_time_picker
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        holder.itemView.isClickable = false
        button = holder.findViewById(R.id.btn_time_picker) as Button
        with(button) {
            text = formatter.format(LocalTime.of(getCurrentHour(), getCurrentMinute()))
            setOnClickListener { showTimePicker() }
        }
    }

    private fun getCurrentHour() =
        sharedPreference.getInt(context.getString(R.string.key_notification_hour), 9)

    private fun getCurrentMinute() =
        sharedPreference.getInt(context.getString(R.string.key_notification_minute), 0)

    private fun showTimePicker() {
        TimePickerDialog(
            context,
            timeSetListener,
            getCurrentHour(),
            getCurrentMinute(),
            is24HourFormat(context)
        ).show()
    }
}