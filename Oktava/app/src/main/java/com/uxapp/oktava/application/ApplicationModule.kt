package com.uxapp.oktava.application

import com.uxapp.oktava.storage.StorageModule
import dagger.Module

@Module(includes = [StorageModule::class])
object ApplicationModule