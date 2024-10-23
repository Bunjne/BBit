package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.local.entity.RocketLaunch

interface SpaceXRepository {
    suspend fun getLaunches(): Result<List<RocketLaunch>>
}