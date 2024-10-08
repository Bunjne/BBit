package com.bunjne.bbit.di

import com.bunjne.bbit.data.local.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun dataModule() = module {
    single {
        createDataStore()
    }
}