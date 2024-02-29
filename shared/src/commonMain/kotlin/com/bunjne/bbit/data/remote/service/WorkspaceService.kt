package com.bunjne.bbit.data.remote.service

import com.bunjne.bbit.data.remote.ApiEndpoints
import com.bunjne.bbit.data.remote.model.WorkspaceResponse
import com.bunjne.bbit.domain.repository.LoginRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

interface WorkspaceService {

    suspend fun getCurrentWorkspaces(page: Int): WorkspaceResponse
}

class WorkspaceServiceImpl(private val httpClient: HttpClient, private val loginRepository: LoginRepository): WorkspaceService {
    override suspend fun getCurrentWorkspaces(page: Int): WorkspaceResponse =
        httpClient.get("${ApiEndpoints.BASE_URL}user/permissions/workspaces") {
            header("Authorization", "Bearer ${loginRepository.token}")

            url {
                parameters.append("page", page.toString())
            }
        }.body()
}