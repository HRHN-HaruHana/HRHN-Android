package com.hrhn.presentation.util

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.hrhn.R
import java.time.LocalDate
import java.time.LocalDateTime

class SharedPreferenceManager(context: Context) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val KEY_ONBOARDING_ALREADY_SHOWN by lazy { context.getString(R.string.key_onboarding_already_shown) }
    private val KEY_NOTIFICATION_ON_OFF by lazy { context.getString(R.string.key_notification_on_off) }
    private val KEY_NOTIFICATION_HOUR by lazy { context.getString(R.string.key_notification_hour) }
    private val KEY_NOTIFICATION_MINUTE by lazy { context.getString(R.string.key_notification_minute) }

    val isOnboardingAlreadyShown: Boolean
        get() = sharedPreferences.getBoolean(KEY_ONBOARDING_ALREADY_SHOWN, false)

    val isNotificationOn: Boolean
        get() = sharedPreferences.getBoolean(KEY_NOTIFICATION_ON_OFF, false)

    fun getNotificationHour() = sharedPreferences.getInt(KEY_NOTIFICATION_HOUR, 9)
    fun getNotificationMinute() = sharedPreferences.getInt(KEY_NOTIFICATION_MINUTE, 0)

    fun getAlarmTime(): LocalDateTime {
        val alarmTime = LocalDate.now().atTime(
            getNotificationHour(),
            getNotificationMinute(),
            0
        )

        return with(alarmTime) {
            if (isBefore(LocalDateTime.now())) this.plusDays(1)
            else this
        }
    }

    fun updateAlarmTime(hourOfDay: Int, minute: Int) {
        sharedPreferences.edit {
            putInt(KEY_NOTIFICATION_HOUR, hourOfDay)
            putInt(KEY_NOTIFICATION_MINUTE, minute)
        }
    }

    fun updateNotificationOnOff(value: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_NOTIFICATION_ON_OFF, value)
        }
    }

    fun updateOnboardingState(value: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_ONBOARDING_ALREADY_SHOWN, value)
        }
    }
}