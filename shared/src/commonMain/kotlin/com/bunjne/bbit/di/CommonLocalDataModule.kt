package com.bunjne.bbit.di

import com.bunjne.bbit.data.datasource.AuthPreferencesDataSource
import com.bunjne.bbit.data.datasource.impl.DefaultAuthPreferencesDataSource
import com.bunjne.bbit.data.local.database.AppDatabase
import com.bunjne.bbit.data.local.database.getRoomDatabase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun commonLocalDataModule() = module {
    singleOf(::DefaultAuthPreferencesDataSource) { bind<AuthPreferencesDataSource>() }
    single {
        getRoomDatabase(get())
    }
    single {
        get<AppDatabase>().workspaceDao()
    }
}