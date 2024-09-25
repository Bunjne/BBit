package com.bunjne.bbit.di

import com.bunjne.bbit.data.data_source.AuthPreferencesDataSource
import com.bunjne.bbit.data.data_source.impl.DefaultAuthPreferencesDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun commonDataModule() = module {
    singleOf(::DefaultAuthPreferencesDataSource) { bind<AuthPreferencesDataSource>() }
}