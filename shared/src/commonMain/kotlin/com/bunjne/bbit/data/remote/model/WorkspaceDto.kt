package com.bunjne.bbit.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkspaceResponse(
    @SerialName("values")
    val values: List<WorkspaceDto>,
    @SerialName("pagelen")
    val pagelen: Int,
    @SerialName("size")
    val size: Int,
    @SerialName("page")
    val page: Int
)

@Serializable
data class WorkspaceDto(
    @SerialName("type")
    val type: String,
    @SerialName("links")
    val links: LinkDto,
    @SerialName("permission")
    val permission: String,
    @SerialName("last_accessed")
    val lastAccessedDate: String,
    @SerialName("added_on")
    val addedOnDate: String,
    @SerialName("user")
    val user: WorkspaceUserDto,
    @SerialName("workspace")
    val workspace: WorkspaceDetailDto
)

@Serializable
data class WorkspaceUserDto(
    @SerialName("type")
    val type: String,
    @SerialName("links")
    val links: LinkDto?,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("display_name")
    val displayName: String
)

@Serializable
data class WorkspaceDetailDto(
    @SerialName("type")
    val type: String,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("name")
    val name: String,
    @SerialName("links")
    val links: LinkDto?
)
