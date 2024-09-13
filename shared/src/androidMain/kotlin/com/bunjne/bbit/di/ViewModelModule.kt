package com.bunjne.bbit.di

import com.bunjne.bbit.BBitAppViewModel
import com.bunjne.bbit.ui.launches.LaunchesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual fun viewModelModule() = module {

    viewModelOf(::LaunchesViewModel)
    viewModelOf(::BBitAppViewModel)
}