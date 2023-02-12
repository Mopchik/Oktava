package com.uxapp.oktava.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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