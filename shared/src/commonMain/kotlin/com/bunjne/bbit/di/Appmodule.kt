package com.bunjne.bbit.di

import com.bunjne.bbit.data.datasource.AuthPreferencesDataSource
import com.bunjne.bbit.data.model.toBearerTokens
import com.bunjne.bbit.data.remote.ApiEndpoints
import com.bunjne.bbit.data.remote.HttpClientProvider
import com.bunjne.bbit.data.remote.HttpClientType
import com.bunjne.bbit.data.remote.plugin.DefaultBearerAuthProvider
import com.bunjne.bbit.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
private fun appModule() = module {
    single {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            encodeDefaults = true
            prettyPrint = true
            isLenient = true
        }
    }

    single<HttpClient>(named(HttpClientType.BITBUCKET)) {
        HttpClientProvider(
            apiUrl = ApiEndpoints.BASE_URL,
//            apiUrl = if (getPlatform() == Platform.ANDROID) "http://10.0.2.2:3008" else "http://127.0.0.1:3008",
            json = get(),
        ).init().config {
            setAuth(get(), get())
        }
    }

    single<HttpClient>(named(HttpClientType.NASA)) {
        HttpClientProvider(
            apiUrl = ApiEndpoints.SPACEX_URL,
            json = get(),
        ).init()
    }

    single<HttpClient>(named(HttpClientType.LOGIN)) {
        HttpClientProvider(
            apiUrl = ApiEndpoints.AUTH_BASE_URL,
            json = get(),
        ).init()
    }
}

fun appModules() = listOf(
    appModule(),
    apiModule(),
    localDataModule(),
    commonLocalDataModule(),
    repositoryModule(),
    useCaseModule(),
    viewModelModule()
)

private fun HttpClientConfig<*>.setAuth(
    authPreferencesDataSource: AuthPreferencesDataSource,
    authRepository: AuthRepository
) {
    install(Auth) {
        providers.add(
            DefaultBearerAuthProvider(
                refreshTokens = {
                    authRepository.refreshToken {
                        markAsRefreshTokenRequest()
                    }.run {
                        authPreferencesDataSource.getAuthTokens().toBearerTokens()
                    }
                },
                loadTokens = {
                    authPreferencesDataSource.getAuthTokens().toBearerTokens()
                },
                sendWithoutRequestCallback = { true },
                realm = null
            )
        )
    }
}
