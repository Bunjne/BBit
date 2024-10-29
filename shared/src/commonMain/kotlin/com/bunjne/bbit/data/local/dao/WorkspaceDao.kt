package com.bunjne.bbit.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bunjne.bbit.data.local.entity.WorkspaceEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkspaceDao {
    @Upsert
    suspend fun insert(items: List<WorkspaceEntity>)

    @Query("SELECT * FROM WorkspaceEntity")
    fun getAllWorkspaces(): Flow<List<WorkspaceEntity>>

    @Query("DELETE FROM WorkspaceEntity")
    suspend fun clearAll()
}