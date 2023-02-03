package com.hrhn

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
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
import javax.inject.Inject

@AndroidEntryPoint
class TodayChallengeWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var repository: ChallengeRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        handleUpdateEvent(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, TodayChallengeWidgetProvider::class.java)
            )
            handleUpdateEvent(context, appWidgetManager, appWidgetIds)
        }
    }

    private fun handleUpdateEvent(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
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
        val emptyText = context.getString(R.string.message_new_challenge)
        val remoteViews = RemoteViews(context.packageName, R.layout.today_widget).apply {
            if (today != null) {
                setViewVisibility(R.id.tv_empty, View.INVISIBLE)
                setViewVisibility(R.id.tv_today_challenge, View.VISIBLE)
                setTextViewText(R.id.tv_today_challenge, today.content)
            } else {
                setTextViewText(R.id.tv_empty, emptyText)
                setViewVisibility(R.id.tv_empty, View.VISIBLE)
                setViewVisibility(R.id.tv_today_challenge, View.INVISIBLE)
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    companion object {
        fun newIntent(context: Context) {
            val intent = Intent(context, TodayChallengeWidgetProvider::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            }
            context.sendBroadcast(intent)
        }
    }
}
