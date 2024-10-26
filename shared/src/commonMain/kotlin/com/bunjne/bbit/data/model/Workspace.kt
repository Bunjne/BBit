package com.bunjne.bbit.data.model

import com.bunjne.bbit.data.local.entity.WorkspaceEntity
import com.bunjne.bbit.data.remote.model.WorkspaceDto


data class Workspace(
    val uuid: String,
    val name: String,
    val profileImage: String?,
    val slug: String
)

fun WorkspaceDto.toExternalModel() = Workspace(
    uuid = workspace.uuid,
    name = workspace.name,
    profileImage = workspace.links?.avatar?.href,
    slug = workspace.slug
)

fun WorkspaceEntity.toExternalModel() = Workspace(
    uuid = id,
    name = name,
    profileImage = imageUrl,
    slug = slug
)