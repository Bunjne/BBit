package com.bunjne.bbit.ui.more

sealed interface MoreUiEvent {

    data object SingOutSuccessFully : MoreUiEvent
}