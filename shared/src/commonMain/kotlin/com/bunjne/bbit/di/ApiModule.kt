package com.bunjne.bbit.di

import com.bunjne.bbit.data.remote.service.LoginService
import com.bunjne.bbit.data.remote.service.LoginServiceImpl
import com.bunjne.bbit.data.remote.service.SpaceXApi
import com.bunjne.bbit.data.remote.service.SpaceXApiImpl
import com.bunjne.bbit.data.remote.service.WorkspaceService
import com.bunjne.bbit.data.remote.service.WorkspaceServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun apiModule() = module {
    singleOf(::SpaceXApiImpl) { bind<SpaceXApi>() }
    singleOf(::LoginServiceImpl) { bind<LoginService>() }
    singleOf(::WorkspaceServiceImpl) { bind<WorkspaceService>() }
}