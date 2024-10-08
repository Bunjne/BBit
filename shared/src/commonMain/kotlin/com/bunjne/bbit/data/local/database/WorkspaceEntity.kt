package com.bunjne.bbit.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkspaceEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    val name: String,
    val imageUrl: String,
    val slug: String,
)
