package com.mustafacol.spacex

import android.app.Application
import com.mustafacol.spacex.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SpacexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpacexApplication)
            modules(appModule)
        }
    }
}