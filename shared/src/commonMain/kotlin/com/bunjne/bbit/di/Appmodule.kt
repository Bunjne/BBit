package com.bunjne.bbit.di

import com.bunjne.bbit.data.remote.ApiEndpoints
import com.bunjne.bbit.data.remote.HttpClientProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
private fun appModule() = module {
    single {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            encodeDefaults = true
        }
    }

    single {
        HttpClientProvider(
            apiUrl = ApiEndpoints.BASE_URL,
            json = get()
        ).provide()
    }
}

fun appModules() = listOf(
    appModule(),
    apiModule(),
    dataModule(),
    repositoryModule(),
    useCaseModule(),
    viewModelModule()
)