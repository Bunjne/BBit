package com.bunjne.bbit.android

import android.app.Application
import com.bunjne.bbit.BuildConfig
import com.bunjne.bbit.di.appModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BBitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BBitApplication)
            modules(
                appModules()
            )
        }

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }
    }
}