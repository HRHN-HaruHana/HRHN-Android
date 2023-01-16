package com.hrhn.presentation

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import com.hrhn.R
import com.hrhn.presentation.ui.screen.addchallenge.AddChallengeActivity
import com.hrhn.presentation.util.AlarmManagerUtil
import com.hrhn.presentation.util.NotificationUtil
import com.hrhn.presentation.util.SharedPreferenceManager

class DailyAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        showNotification(context)
        setNextAlarm(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = context.getString(R.string.notification_channel_id)
        val addIntent = Intent(context, AddChallengeActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(addIntent)
            getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationUtil(channelId).createNotification(
            context,
            title = context.getString(R.string.app_name),
            content = context.getString(R.string.notification_content),
            pendingIntent = pendingIntent
        )

        notificationManager.notify(1, notification)
    }

    private fun setNextAlarm(context: Context) {
        val alarmManager = AlarmManagerUtil(context)
        val sharedPreferenceManager = SharedPreferenceManager(context)
        val time = sharedPreferenceManager.getAlarmTime()
        alarmManager.setAlarm(time)
    }
}