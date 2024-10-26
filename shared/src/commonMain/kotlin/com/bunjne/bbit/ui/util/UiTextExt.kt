package com.bunjne.bbit.ui.util

import com.bunjne.bbit.data.RootError
import com.bunjne.bbit.data.model.DataError
import com.bunjne.bbit.resources.Res
import com.bunjne.bbit.resources.error_disk_full
import com.bunjne.bbit.resources.error_internal
import com.bunjne.bbit.resources.error_no_internet
import com.bunjne.bbit.resources.error_request_timeout
import com.bunjne.bbit.resources.error_serialization
import com.bunjne.bbit.resources.error_server
import com.bunjne.bbit.ui.model.UiText

fun DataError.asUiText(): UiText = when (this) {
    DataError.Network.NO_INTERNET -> UiText.StringRes(Res.string.error_no_internet)
    DataError.Network.SERVER_ERROR -> UiText.StringRes(Res.string.error_server)
    DataError.Network.REQUEST_TIMEOUT -> UiText.StringRes(Res.string.error_request_timeout)
    DataError.Network.SERIALIZATION -> UiText.StringRes(Res.string.error_serialization)
    DataError.Network.INTERNAL -> UiText.StringRes(Res.string.error_internal)
    DataError.Local.DISK_FULL -> UiText.StringRes(Res.string.error_disk_full)
    is DataError.NetworkError -> UiText.DynamicString(this.apiError.error.message) // Use error from API only
}

fun RootError.asUiText(): UiText = when (this) {
    is DataError -> this.asUiText()
    else -> UiText.StringRes(Res.string.error_internal)
}