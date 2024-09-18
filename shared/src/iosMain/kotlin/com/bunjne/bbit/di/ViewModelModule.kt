package com.bunjne.bbit.di

import com.bunjne.bbit.BBitAppViewModel
import com.bunjne.bbit.ui.launches.LaunchesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun viewModelModule() = module {

    singleOf(::LaunchesViewModel)
    singleOf(::BBitAppViewModel)
}