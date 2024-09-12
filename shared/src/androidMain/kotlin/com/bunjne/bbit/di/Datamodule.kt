package com.bunjne.bbit.di

import android.app.Application
import com.bunjne.bbit.data.local.database.DatabaseDriverFactory
import com.bunjne.bbit.data.local.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun dataModule() = module {
    single {
        DatabaseDriverFactory(androidContext())
    }
    single {
        createDataStore(androidContext())
    }
}