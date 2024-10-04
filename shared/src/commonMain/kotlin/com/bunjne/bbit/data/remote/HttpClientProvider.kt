package com.bunjne.bbit.data.remote

import com.bunjne.bbit.data.remote.ApiConstants.API_TIMEOUT_MS
import com.bunjne.bbit.data.remote.error.ApiException
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

enum class HttpClientType {
    BITBUCKET,
    LOGIN,
    NASA
}

class HttpClientProvider(
    private val apiUrl: String,
    private val json: Json,
) {

    fun init() = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = API_TIMEOUT_MS
            connectTimeoutMillis = API_TIMEOUT_MS
        }

        defaultRequest {
            url(apiUrl)
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        expectSuccess = true

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val clientException = exception as? ClientRequestException
                    ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                val body = runBlocking {
                    exceptionResponse.body<ApiException>()
                }
                Napier.e("handleResponseException: ${exceptionResponse.status.value} $body")
            }
        }
    }

    fun HttpClient.validateResponse(): HttpClient {

        return this
    }
}