package com.bunjne.bbit.di

import com.bunjne.bbit.data.local.database.getAppDatabaseBuilder
import com.bunjne.bbit.data.local.datastore.createDataStore
import org.koin.dsl.module

actual fun localDataModule() = module {
    single {
        createDataStore()
    }
    single {
        getAppDatabaseBuilder()
    }
}