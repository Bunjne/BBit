package com.bunjne.bbit.di

import com.bunjne.bbit.data.repository.SpaceXRepositoryImpl
import com.bunjne.bbit.usecase.repository.SpaceXRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun repositoryModule() = module{
    singleOf(::SpaceXRepositoryImpl) { bind<SpaceXRepository>() }
}