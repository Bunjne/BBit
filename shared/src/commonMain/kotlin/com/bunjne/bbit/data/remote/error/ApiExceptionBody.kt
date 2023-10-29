package com.bunjne.bbit.data.remote.error

import com.bunjne.bbit.Constants.EMPTY_STRING
import com.bunjne.bbit.data.remote.StatusCode.INTERNAL_ERROR
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiExceptionBody(
    @SerialName("code") val code: Int = INTERNAL_ERROR,
    @SerialName("message") val message: String = EMPTY_STRING
)

data class ApiException(
    val error: ApiExceptionBody?
) : IOException("${error?.code} ${error?.message}")
