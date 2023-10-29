package com.bunjne.bbit.data.remote

import com.bunjne.bbit.data.remote.ApiConstants.API_TIMEOUT
import com.bunjne.bbit.data.remote.ApiConstants.CONNECT_ATTEMPT
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json

class HttpClientProvider(
    private val apiUrl: String,
    private val json: Json
) {
    fun provide() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = URLBuilder(host = "").buildString()
            }
        }

        engine {
            endpoint {
                connectTimeout = API_TIMEOUT
                connectAttempts = CONNECT_ATTEMPT
            }
        }
    }
}