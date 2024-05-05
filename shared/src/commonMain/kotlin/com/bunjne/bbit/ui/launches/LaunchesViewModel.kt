package com.bunjne.bbit.ui.launches

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.domain.repository.SpaceXRepository
import com.bunjne.bbit.domain.usecase.ViewState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchesViewModel(
    private val spaceXRepository: SpaceXRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaunchesUIState())
    val uiState = _uiState.asStateFlow()

    fun onUIEvent(uiEvent: LaunchesUIEvent) {
        when (uiEvent) {
            is LaunchesUIEvent.OnLaunchClicked -> {
                _uiState.value = _uiState.value.copy(selectedLaunch = uiEvent.launch)
            }
        }
    }

    fun getLaunches() {
        viewModelScope.launch {
            when (val launches = spaceXRepository.getLaunches()) {
                is DataState.Success -> {
                    _uiState.value = LaunchesUIState(
                        launchesState = ViewState.Success(
                            launches.data
                        )
                    )
                }

                is DataState.Error -> {
                    _uiState.value =
                        LaunchesUIState(ViewState.Error(launches.statusCode ?: 0, launches.message))
                }
            }
        }
    }
}