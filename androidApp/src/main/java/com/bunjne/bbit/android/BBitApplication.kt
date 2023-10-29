package com.bunjne.bbit.android

import android.app.Application
import com.bunjne.bbit.di.appModules
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class BBitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BBitApplication)
            androidLogger(logLevel())
//            modules(
//                appModules()
//            )
        }
    }

    private fun logLevel() = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE
}