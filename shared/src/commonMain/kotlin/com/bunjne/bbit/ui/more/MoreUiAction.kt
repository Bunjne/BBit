package com.bunjne.bbit.ui.more

sealed interface MoreUiAction {

    data object onSignOut : MoreUiAction
}