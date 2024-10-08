package com.bunjne.bbit.di

import com.bunjne.bbit.data.repository.AuthRepositoryImpl
import com.bunjne.bbit.data.repository.WorkspaceRepositoryImpl
import com.bunjne.bbit.domain.repository.AuthRepository
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun repositoryModule() = module{
//    singleOf(::SpaceXRepositoryImpl) { bind<SpaceXRepository>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::WorkspaceRepositoryImpl) { bind<WorkspaceRepository>() }
}