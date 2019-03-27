package sk.luky.arduino

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.util.*

class ArduinoWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        println("update")
        val views = RemoteViews(context.packageName, R.layout.widget_dark)
        views.setOnClickPendingIntent(R.id.left, getNavigationIntent(context, ACTION_LEFT, appWidgetIds))
        views.setOnClickPendingIntent(R.id.right, getNavigationIntent(context, ACTION_RIGHT, appWidgetIds))
        views.setOnClickPendingIntent(R.id.settings, getSettingsActivityIntent(context, appWidgetIds))

        views.addView(R.id.flipper, createDataView(context, "current time", Date().toString()))
        views.addView(R.id.flipper, createDataView(context, "test", "abc"))

        appWidgetManager.updateAppWidget(appWidgetIds, views)
    }

    private fun createDataView(context: Context, description: String, value: String) =
        RemoteViews(context.packageName, R.layout.data_light).apply {
            setTextViewText(R.id.description, description)
            setTextViewText(R.id.value, value)
        }

    private fun getNavigationIntent(context: Context, action: String, appWidgetIds: IntArray) = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, javaClass).apply {
            setAction(action)
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun getSettingsActivityIntent(context: Context, appWidgetIds: IntArray) = PendingIntent.getActivity(
        context,
        1,
        Intent(context, SettingsActivity::class.java).apply { putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds) },
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        val views = RemoteViews(context.packageName, R.layout.widget_dark)
        if (intent == null) return
        val widgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)
        when (intent.action) {
            ACTION_LEFT -> {
                views.showPrevious(R.id.flipper)
                AppWidgetManager.getInstance(context).updateAppWidget(widgetIds, views)
            }
            ACTION_RIGHT -> {
                views.showNext(R.id.flipper)
                AppWidgetManager.getInstance(context).updateAppWidget(widgetIds, views)
            }
        }
    }

    companion object {
        const val ACTION_LEFT = "sk.luky.arduino.left"
        const val ACTION_RIGHT = "sk.luky.arduino.right"
    }
}