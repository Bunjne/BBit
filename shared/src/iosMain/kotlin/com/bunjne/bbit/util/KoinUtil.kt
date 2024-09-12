package com.bunjne.bbit.util

import com.bunjne.bbit.di.appModules
import org.koin.core.context.startKoin

fun doInitKoin() {
    startKoin {
        modules(appModules())
    }
}