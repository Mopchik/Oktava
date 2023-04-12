package com.uxapp.oktava.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.uxapp.oktava.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class App : Application() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var prefs: SharedPreferences
    val applicationDefaultScope = CoroutineScope(Dispatchers.Default)
    lateinit var applicationComponent: ApplicationComponent

    private var mTracker: Tracker? = null

    /**
     * Gets the default [Tracker] for this [Application].
     * @return tracker
     */
    @Synchronized
    fun getDefaultTracker(): Tracker? {
        if (mTracker == null) {
            val analytics: GoogleAnalytics = GoogleAnalytics.getInstance(this)
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker("358147352")
        }
        return mTracker
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("KOSTIK", "Application created")
        applicationComponent = DaggerApplicationComponent
            .factory()
            .create(this, applicationDefaultScope, contentResolver)
        applicationComponent.inject(this)
    }

    companion object {
        /**
         * Shortcut to get [App] instance from any context, e.g. from Activity.
         */
        fun get(context: Context): App = context.applicationContext as App
    }
}