package com.bunjne.bbit.data.remote

import com.bunjne.bbit.data.remote.ApiConstants.API_TIMEOUT_MS
import com.bunjne.bbit.data.remote.ApiConstants.CONNECT_ATTEMPT
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class HttpClientProvider(
    private val apiUrl: String,
    private val json: Json
) {
    fun provide() = httpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }
//    fun provide() = HttpClient(CIO) {
//        install(ContentNegotiation) {
//            json(json)
//        }
//        install(Logging)
//        defaultRequest {
//            url {
//                protocol = URLProtocol.HTTPS
//                host = URLBuilder(host = apiUrl).buildString()
//            }
//        }
//        engine {
//            endpoint {
//                connectTimeout = API_TIMEOUT_MS
//                connectAttempts = CONNECT_ATTEMPT
//            }
//
//            https {
//                serverName = "api.spacexdata.com"
//                cipherSuites = CIOCipherSuites.SupportedSuites
//                trustManager = myCustomTrustManager
//                random = mySecureRandom
//                addKeyStore(myKeyStore, myKeyStorePassword)
//            }
//        }
//    }
}