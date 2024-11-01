package com.bunjne.bbit.di

import com.bunjne.bbit.domain.usecase.SignOutUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun useCaseModule() = module {
    singleOf(::SignOutUseCase)
}