package com.hrhn.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hrhn.presentation.util.AlarmManagerUtil
import com.hrhn.presentation.util.SharedPreferenceManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            val alarmManager = AlarmManagerUtil(context)
            val sharedPreferenceManager = SharedPreferenceManager(context)
            val time = sharedPreferenceManager.getAlarmTime()
            alarmManager.setAlarm(time)
        }
    }
}