package com.bunjne.bbit.di

import com.bunjne.bbit.presentation.launches.LaunchesViewModel
import org.koin.dsl.module

actual fun viewModelModule() = module {
    single {
        LaunchesViewModel(get())
    }
}