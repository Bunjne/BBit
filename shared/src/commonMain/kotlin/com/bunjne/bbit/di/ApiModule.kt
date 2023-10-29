package com.bunjne.bbit.di

import com.bunjne.bbit.data.remote.service.SpaceXApi
import com.bunjne.bbit.data.remote.service.SpaceXApiImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun apiModule() = module {
    singleOf(::SpaceXApiImpl) { bind<SpaceXApi>() }
}