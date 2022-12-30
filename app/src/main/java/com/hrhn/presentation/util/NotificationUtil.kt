package com.hrhn.presentation.util

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hrhn.R

class NotificationUtil(private val channelId: String) {
    fun createNotification(
        context: Context,
        title: String,
        content: String,
        pendingIntent: PendingIntent?
    ): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_hrhn_noti_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
}