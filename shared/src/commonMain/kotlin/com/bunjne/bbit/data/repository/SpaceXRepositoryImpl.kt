package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.data.remote.service.SpaceXApi
import com.bunjne.bbit.domain.repository.SpaceXRepository

class SpaceXRepositoryImpl(
    private val api: SpaceXApi
) : BaseRepository(), SpaceXRepository {

    override suspend fun getLaunches(): DataState<List<RocketLaunch>> {

        return execute {
            api.getAllLaunches()
        }
    }
}