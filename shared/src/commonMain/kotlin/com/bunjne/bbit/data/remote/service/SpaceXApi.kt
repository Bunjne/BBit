package com.bunjne.bbit.data.remote.service

import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.data.remote.ApiEndpoints.SPACEX_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface SpaceXApi {
    suspend fun getAllLaunches(): List<RocketLaunch>
}

class SpaceXApiImpl(private val httpClient: HttpClient) : SpaceXApi {

    override suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient.get("${SPACEX_URL}launches").body()
    }
}