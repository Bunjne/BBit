package com.bunjne.bbit.di

import com.bunjne.bbit.ui.launches.LaunchesViewModel
import org.koin.dsl.module

actual fun viewModelModule() = module {
    single {
        LaunchesViewModel(get())
    }
}