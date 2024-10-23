package com.bunjne.bbit.di

import com.bunjne.bbit.ui.BBitAppViewModel
import com.bunjne.bbit.ui.login.LoginViewModel
import com.bunjne.bbit.ui.workspaceList.WorkspaceListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual fun viewModelModule() = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::WorkspaceListViewModel)
    viewModelOf(::BBitAppViewModel)
}