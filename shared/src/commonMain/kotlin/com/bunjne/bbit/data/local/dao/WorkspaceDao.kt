package com.bunjne.bbit.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bunjne.bbit.data.local.entity.WorkspaceEntity


@Dao
interface WorkspaceDao {
    @Upsert
    suspend fun insert(items: List<WorkspaceEntity>)

    @Query("SELECT * FROM WorkspaceEntity")
    suspend fun getAllWorkspaces(): List<WorkspaceEntity>
}