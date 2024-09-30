package com.bunjne.bbit.di

import com.bunjne.bbit.domain.usecase.GetWorkspacesUseCase
import org.koin.dsl.module

fun useCaseModule() = module {
    single { GetWorkspacesUseCase(get()) }
}