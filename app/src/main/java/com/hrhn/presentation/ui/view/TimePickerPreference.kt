package com.hrhn.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.hrhn.R

class TimePickerPreference constructor(
    context: Context, attrs: AttributeSet?
) : Preference(context, attrs) {

    init {
        widgetLayoutResource = R.layout.preference_time_picker
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        holder.itemView.isClickable = false
        val button: Button = holder.findViewById(R.id.btn_time_picker) as Button
        button.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        // TODO
    }
}