package sk.luky.arduino

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.widget.RemoteViews

class SettingsActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    override fun onBackPressed() {
        val widgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(packageName, R.layout.widget_dark)
        println(widgetIds)
        appWidgetManager.updateAppWidget(widgetIds, remoteViews)
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds) })
        finish()
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings)

            findPreference("update_period").apply {
                onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
                    preference.summary = "$value min"
                    true
                }

                onPreferenceChangeListener.onPreferenceChange(this, PreferenceManager.getDefaultSharedPreferences(this.context).getString(this.key, ""))
            }
        }
    }
}
