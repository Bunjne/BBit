package com.bunjne.bbit.di

import com.bunjne.bbit.data.remote.HttpClientType
import com.bunjne.bbit.data.remote.service.LoginService
import com.bunjne.bbit.data.remote.service.LoginServiceImpl
import com.bunjne.bbit.data.remote.service.SpaceXApi
import com.bunjne.bbit.data.remote.service.SpaceXApiImpl
import com.bunjne.bbit.data.remote.service.WorkspaceService
import com.bunjne.bbit.data.remote.service.WorkspaceServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun apiModule() = module {
    single<LoginService> {
        LoginServiceImpl(
            get(named(HttpClientType.LOGIN))
        )
    }
    single<WorkspaceService> {
        WorkspaceServiceImpl(
            get(named(HttpClientType.BITBUCKET)),
            get()
        )
    }
    single<SpaceXApi> {
        SpaceXApiImpl(
            get(named(HttpClientType.NASA))
        )
    }
}