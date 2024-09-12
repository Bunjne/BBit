package com.bunjne.bbit.data.remote.service

import com.bunjne.bbit.data.data_source.AuthDataStore
import com.bunjne.bbit.data.remote.model.WorkspaceResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface WorkspaceService {

    suspend fun getCurrentWorkspaces(page: Int): WorkspaceResponse
}

class WorkspaceServiceImpl(
    private val httpClient: HttpClient,
    private val authDataStore: AuthDataStore,
) : WorkspaceService {
    override suspend fun getCurrentWorkspaces(page: Int): WorkspaceResponse =
        httpClient.get("user/permissions/workspaces") {
            url {
                parameters.append("page", page.toString())
            }
        }.body()
}