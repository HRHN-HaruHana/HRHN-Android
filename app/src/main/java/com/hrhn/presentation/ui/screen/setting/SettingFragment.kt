package com.hrhn.presentation.ui.screen.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.hrhn.R
import com.hrhn.presentation.DailyAlarmReceiver
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val alarmManager by lazy {
        requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private val pendingIntent by lazy {
        PendingIntent.getBroadcast(
            requireContext(),
            1,
            Intent(requireContext(), DailyAlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val notificationOnOffKey = context?.getString(R.string.key_notification_on_off)
        val hourKey = context?.getString(R.string.key_notification_hour)
        val minuteKey = context?.getString(R.string.key_notification_minute)

        if (key == notificationOnOffKey) {
            with(sharedPreferences) {
                if (getBoolean(notificationOnOffKey, false)) {
                    val notificationTime = LocalDate.now().atTime(
                        getInt(hourKey, 9),
                        getInt(minuteKey, 0),
                        0
                    )

                    if (notificationTime.isBefore(LocalDateTime.now())) {
                        setAlarm(notificationTime.plusDays(1))
                    } else {
                        setAlarm(notificationTime)
                    }
                } else {
                    cancelAlarm()
                }
            }
        }
    }

    private fun setAlarm(time: LocalDateTime) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            1000 * 60,
            pendingIntent
        )
    }

    private fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
    }
}