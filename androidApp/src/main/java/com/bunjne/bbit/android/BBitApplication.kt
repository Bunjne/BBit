package com.bunjne.bbit.android

import android.app.Application
import com.bunjne.bbit.BuildConfig
import com.bunjne.bbit.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class BBitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(this@BBitApplication).init()

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }
    }
}