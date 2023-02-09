package com.hrhn.presentation.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.RemoteViews
import com.hrhn.R
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@AndroidEntryPoint
class TodayChallengeWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var repository: ChallengeRepository
    private val intentFilter by lazy {
        IntentFilter().apply {
            addAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            addAction(Intent.ACTION_DATE_CHANGED)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(ACTION_DAILY_WIDGET_UPDATE)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        handleUpdateEvent(context)
        setNextAlarm(context)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        handleUpdateEvent(context)
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.action.also { action ->
            if (intentFilter.hasAction(action)) {
                handleUpdateEvent(context)
                if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE).not()) {
                    setNextAlarm(context)
                }
            }
        }
    }

    private fun handleUpdateEvent(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context, TodayChallengeWidgetProvider::class.java)
        )
        CoroutineScope(Dispatchers.Main).launch {
            val today = async { getTodayChallenge().first() }
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, today.await())
            }
        }
    }

    private fun getTodayChallenge(): Flow<Challenge?> {
        return repository.getChallengesWithPeriod(
            LocalDate.now().atStartOfDay(),
            LocalDate.now().plusDays(1).atStartOfDay()
        ).map {
            if (it.isNotEmpty()) it.getOrNull(0) else null
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        today: Challenge?
    ) {
        val emptyText = context.getString(R.string.today_widget_placeholder)
        val remoteViews = RemoteViews(context.packageName, R.layout.today_widget).apply {
            setTextViewText(R.id.tv_today_challenge, today?.content ?: emptyText)
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    private fun setNextAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getDailyUpdatePendingIntent(context)
        val time = LocalDate.now().plusDays(1).atStartOfDay()
            .atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, time, pendingIntent)
    }

    private fun getDailyUpdatePendingIntent(context: Context): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, TodayChallengeWidgetProvider::class.java).apply {
                action = ACTION_DAILY_WIDGET_UPDATE
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getDailyUpdatePendingIntent(context)
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private const val ACTION_DAILY_WIDGET_UPDATE = "ACTION_DAILY_WIDGET_UPDATE"

        fun newIntent(context: Context) {
            val intent = Intent(context, TodayChallengeWidgetProvider::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            }
            context.sendBroadcast(intent)
        }
    }
}
