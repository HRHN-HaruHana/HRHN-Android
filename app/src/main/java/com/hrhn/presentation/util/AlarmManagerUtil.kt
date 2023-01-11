package com.hrhn.presentation.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hrhn.presentation.DailyAlarmReceiver
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmManagerUtil(context: Context) {
    private val alarmManager by lazy { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    private val pendingIntent by lazy {
        PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, DailyAlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun setAlarm(time: LocalDateTime) {
        val startTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            startTime,
            pendingIntent
        )
    }

    fun cancelAlarm() = alarmManager.cancel(pendingIntent)
}