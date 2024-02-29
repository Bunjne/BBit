package com.bunjne.bbit.data.remote.service

import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.data.remote.ApiEndpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface SpaceXApi {
    suspend fun getAllLaunches(): List<RocketLaunch>
}

class SpaceXApiImpl(private val httpClient: HttpClient) : SpaceXApi {

    override suspend fun getAllLaunches(): List<RocketLaunch> =
        httpClient.get("${ApiEndpoints.SPACEX_URL}launches").body()
}