package com.uxapp.oktava.application

import android.content.Context
import android.content.SharedPreferences
import com.uxapp.oktava.utils.AppScope
import com.uxapp.oktava.utils.ContextOwnerQualifier
import com.uxapp.oktava.utils.CoroutineScopeQualifier
import com.uxapp.oktava.utils.Layer
import com.uxapp.oktava.viewmodels.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope

@Component(modules = [ApplicationModule::class])
@AppScope
interface ApplicationComponent {
    val viewModelFactory: ViewModelFactory
    val prefs: SharedPreferences
    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(@BindsInstance
                   @ContextOwnerQualifier(Layer.APP)
                   applicationContext: Context,
                   @BindsInstance
                   @CoroutineScopeQualifier(Layer.APP)
                   applicationScope: CoroutineScope
        ): ApplicationComponent
    }
    fun inject(app: App)
    // fun mainActivityComponent(): MainActivityComponent
}