package sk.luky.arduino

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import java.net.HttpURLConnection
import java.net.URL

class FlipperService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = FlipperRemoteViewsFactory(applicationContext, intent)

    private class FlipperRemoteViewsFactory(private val context: Context, private val intent: Intent) : RemoteViewsFactory {

        private var data: List<Pair<String, String>> = emptyList()

        override fun onCreate() {
            val connection = URL("aaaa").openConnection() as HttpURLConnection
            connection.connect()
            connection.inputStream.bufferedReader()
        }

        override fun getLoadingView(): RemoteViews {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemId(position: Int): Long = position.toLong()

        override fun onDataSetChanged() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun hasStableIds(): Boolean = true

        override fun getViewAt(position: Int): RemoteViews {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getViewTypeCount(): Int = 1

        override fun onDestroy() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}