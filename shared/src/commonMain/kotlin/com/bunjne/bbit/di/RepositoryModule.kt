package com.bunjne.bbit.di

import com.bunjne.bbit.data.repository.LoginRepositoryImpl
import com.bunjne.bbit.data.repository.SpaceXRepositoryImpl
import com.bunjne.bbit.data.repository.WorkspaceRepositoryImpl
import com.bunjne.bbit.domain.repository.LoginRepository
import com.bunjne.bbit.domain.repository.SpaceXRepository
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun repositoryModule() = module{
    singleOf(::SpaceXRepositoryImpl) { bind<SpaceXRepository>() }
    singleOf(::LoginRepositoryImpl) { bind<LoginRepository>() }
    singleOf(::WorkspaceRepositoryImpl) { bind<WorkspaceRepository>() }
}