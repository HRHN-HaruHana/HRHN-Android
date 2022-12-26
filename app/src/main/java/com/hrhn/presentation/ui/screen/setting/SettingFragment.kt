package com.hrhn.presentation.ui.screen.setting

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.hrhn.R
import com.hrhn.presentation.DailyAlarmReceiver
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val notificationOnOffKey by lazy { requireContext().getString(R.string.key_notification_on_off) }
    private var hasNotificationPermission: Boolean = false
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermission = isGranted
            if (!isGranted) {
                resetOnOff()
            }
        }

    private fun resetOnOff() {
        findPreference<SwitchPreferenceCompat>(notificationOnOffKey)?.isChecked = false
    }

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
        hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
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
        val hourKey = context?.getString(R.string.key_notification_hour)
        val minuteKey = context?.getString(R.string.key_notification_minute)

        if (key == notificationOnOffKey) {
            with(sharedPreferences) {
                if (getBoolean(notificationOnOffKey, false)) {
                    if (hasNotificationPermission) {
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
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