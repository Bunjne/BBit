package com.bunjne.bbit.domain.model

import com.bunjne.bbit.data.remote.model.WorkspaceDto


data class Workspace(
    val uuid: String,
    val name: String,
    val profileImage: String?,
    val slug: String
)

fun WorkspaceDto.toWorkspace() = Workspace(
    uuid = workspace.uuid,
    name = workspace.name,
    profileImage = workspace.links?.avatar?.href,
    slug = workspace.slug
)