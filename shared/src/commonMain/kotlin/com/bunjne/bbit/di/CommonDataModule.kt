package com.bunjne.bbit.di

import com.bunjne.bbit.data.data_source.AuthDataStore
import com.bunjne.bbit.data.data_source.DefaultAuthDataStore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun commonDataModule() = module {
    singleOf(::DefaultAuthDataStore) { bind<AuthDataStore>() }
}