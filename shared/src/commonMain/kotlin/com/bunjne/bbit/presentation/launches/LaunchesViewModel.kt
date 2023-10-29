package com.bunjne.bbit.presentation.launches

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.usecase.ViewState
import com.bunjne.bbit.usecase.repository.SpaceXRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchesViewModel(
    private val spaceXRepository: SpaceXRepository
): ViewModel() {

    private val _launchesViewState = MutableStateFlow<ViewState<List<RocketLaunch>>>(ViewState.Loading)
    val launchesViewState = _launchesViewState.asStateFlow()

    fun getLaunches() {
        viewModelScope.launch {
            when (val launches = spaceXRepository.getLaunches()) {
                is DataState.Success -> {
                    _launchesViewState.value = ViewState.Success(launches.data ?: emptyList())
                }
                else -> {
                    _launchesViewState.value = ViewState.Error("To be handled")
                }
            }
        }
    }
}