package com.bunjne.bbit.data.remote.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiException(
    @SerialName("error") val error: ApiErrorBody,
    @SerialName("type") val type: String
)

@Serializable
data class ApiErrorBody(
    @SerialName("data") val data: ApiErrorData,
    @SerialName("detail") val detail: String,
    @SerialName("fields") val fields: ApiErrorFields,
    @SerialName("id") val id: String,
    @SerialName("message") val message: String
)

@Serializable
data class ApiErrorData(
    @SerialName("extra") val extra: String
)

@Serializable
data class ApiErrorFields(
    @SerialName("src") val src: List<String>
)