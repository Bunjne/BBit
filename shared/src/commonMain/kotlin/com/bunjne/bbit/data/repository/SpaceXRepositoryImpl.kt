package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.local.database.Database
import com.bunjne.bbit.data.local.database.DatabaseDriverFactory
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.data.remote.service.SpaceXApi
import com.bunjne.bbit.usecase.repository.SpaceXRepository

class SpaceXRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: SpaceXApi
) : BaseRepository(), SpaceXRepository {

    private val database = Database(databaseDriverFactory)

    override suspend fun getLaunches(): DataState<List<RocketLaunch>> {
//        val cachedLaunches = database.getAllLaunches()
        val cachedLaunches = listOf<RocketLaunch>()

        return execute {
            cachedLaunches.ifEmpty {
                api.getAllLaunches().also {
                    database.clearDatabase()
                    database.createLaunches(it)
                }
            }
        }
    }
}