package com.bunjne.bbit.data.remote.plugin

import io.github.aakira.napier.Napier
import io.ktor.client.plugins.auth.AuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.auth.AuthScheme
import io.ktor.http.auth.HttpAuthHeader

/**
 * A wrapper class of [BearerAuthProvider] to support refresh token handling
 * when getting an API response with status code 401 and www-authenticate: OAuth realm="Bitbucket.org HTTP"
 * Note that, every APIs use Bearer authorization but, auth scheme = OAuth is not supported by [BearerAuthProvider]
 * */
class DefaultBearerAuthProvider(
    private val refreshTokens: suspend RefreshTokensParams.() -> BearerTokens?,
    private val loadTokens: suspend () -> BearerTokens?,
    private val sendWithoutRequestCallback: (HttpRequestBuilder) -> Boolean = { true },
    private val realm: String?
) : AuthProvider {

    @Suppress("OverridingDeprecatedMember")
    @Deprecated("Please use sendWithoutRequest function instead")
    override val sendWithoutRequest: Boolean
        get() = error("Deprecated")


    override fun sendWithoutRequest(request: HttpRequestBuilder): Boolean =
        sendWithoutRequestCallback(request)

    /**
     * Checks if current provider is applicable to the request.
     */
    override fun isApplicable(auth: HttpAuthHeader): Boolean {
        if (auth.authScheme != AuthScheme.Bearer
            && auth.authScheme != AuthScheme.OAuth
            && auth.authScheme != "BitbucketCustom"
        ) {
            println("Bearer Auth Provider is not applicable for $auth")
            Napier.i("Bearer Auth Provider is not applicable for $auth")
            return false
        }
        val isSameRealm = when {
            realm == null -> true
            auth !is HttpAuthHeader.Parameterized -> false
            else -> auth.parameter("realm") == realm
        }
        if (!isSameRealm) {
            Napier.i("Bearer Auth Provider is not applicable for this realm")
        }
        println("isSameRealm $isSameRealm")
        return isSameRealm
    }

    /**
     * Adds an authentication method headers and credentials.
     */
    override suspend fun addRequestHeaders(
        request: HttpRequestBuilder,
        authHeader: HttpAuthHeader?
    ) {
        val token = loadTokens() ?: return

        request.headers {
            val tokenValue = "Bearer ${token.accessToken}"
            if (contains(HttpHeaders.Authorization)) {
                remove(HttpHeaders.Authorization)
            }
            append(HttpHeaders.Authorization, tokenValue)
        }
    }

    override suspend fun refreshToken(response: HttpResponse): Boolean {
        val newToken = refreshTokens(
            RefreshTokensParams(
                response.call.client,
                response,
                loadTokens()
            )
        )
        return newToken != null
    }
}