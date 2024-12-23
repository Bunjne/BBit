package com.bunjne.bbit.di

import com.bunjne.bbit.braodcaster.AndroidNetworkManager
import com.bunjne.bbit.braodcaster.NetworkManager
import com.bunjne.bbit.data.local.database.getAppDatabaseBuilder
import com.bunjne.bbit.data.local.datastore.createDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun localDataModule() = module {
    single {
        createDataStore(androidContext())
    }
    single {
        getAppDatabaseBuilder(androidContext())
    }
    single<NetworkManager> {
        AndroidNetworkManager(androidContext())
    }
}