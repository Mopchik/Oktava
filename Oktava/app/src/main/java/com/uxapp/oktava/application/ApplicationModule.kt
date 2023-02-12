package com.uxapp.oktava.application

import com.uxapp.oktava.storage.StorageModule
import dagger.Module
import dagger.Provides

@Module(includes = [StorageModule::class])
object ApplicationModule