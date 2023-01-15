package com.uxapp.oktava.utils

import javax.inject.Qualifier
import javax.inject.Scope

@Scope
annotation class AppScope

enum class Layer{
    APP, ACTIVITY, FRAGMENT
}
@Qualifier
annotation class ContextOwnerQualifier(val layer: Layer)
@Qualifier
annotation class CoroutineScopeQualifier(val layer: Layer)