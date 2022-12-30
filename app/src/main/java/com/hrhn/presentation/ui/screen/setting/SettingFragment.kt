package com.hrhn.presentation.ui.screen.setting

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.hrhn.R
import com.hrhn.presentation.util.AlarmManagerUtil
import com.hrhn.presentation.util.SharedPreferenceManager

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val keyNotificationOnOff by lazy { requireContext().getString(R.string.key_notification_on_off) }
    private val keyHour by lazy { requireContext().getString(R.string.key_notification_hour) }
    private val keyMinute by lazy { requireContext().getString(R.string.key_notification_minute) }

    private var hasNotificationPermission: Boolean = false
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermission = isGranted
            if (!isGranted) {
                resetOnOff()
            }
        }

    private fun resetOnOff() {
        findPreference<SwitchPreferenceCompat>(keyNotificationOnOff)?.isChecked = false
    }

    private val alarmManager by lazy { AlarmManagerUtil(requireContext()) }
    private val sharedPreferenceManager by lazy { SharedPreferenceManager(requireContext()) }

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
        if (key == keyNotificationOnOff || key == keyHour || key == keyMinute) {
            if (sharedPreferenceManager.isNotificationOn) {
                updateAlarm()
            } else {
                alarmManager.cancelAlarm()
            }
        }
    }

    private fun updateAlarm() {
        if (hasNotificationPermission) {
            alarmManager.setAlarm(sharedPreferenceManager.getAlarmTime())
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}