package com.bunjne.bbit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunjne.bbit.data.remote.model.WorkspaceDto

@Entity
data class WorkspaceEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    val name: String,
    val imageUrl: String,
    val slug: String,
)

fun WorkspaceDto.toEntity() = WorkspaceEntity(
    id = workspace.uuid,
    type = workspace.type,
    name = workspace.name,
    imageUrl = workspace.links?.avatar?.href ?: "",
    slug = workspace.slug
)