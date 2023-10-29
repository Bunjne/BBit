package com.bunjne.bbit.usecase.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.local.entity.RocketLaunch

interface SpaceXRepository {
    suspend fun getLaunches(): DataState<List<RocketLaunch>>
}