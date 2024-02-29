package com.bunjne.bbit.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import java.util.concurrent.TimeUnit

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(ApiConstants.API_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        }
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("HTTP Client", message)
//                Napier.v("HTTP Client", null, message)
            }
        }
        level = LogLevel.ALL
    }
}
