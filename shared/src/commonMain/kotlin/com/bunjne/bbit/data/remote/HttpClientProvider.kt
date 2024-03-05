package com.bunjne.bbit.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json



class HttpClientProvider(
    private val apiUrl: String,
    private val json: Json
) {
//    fun provide() = httpClient {
//        install(ContentNegotiation) {
//            json(json)
//        }
//    }
    fun provide() = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = ApiConstants.API_TIMEOUT_MS
            connectTimeoutMillis = ApiConstants.API_TIMEOUT_MS
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = URLBuilder(host = apiUrl).buildString()
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }
}