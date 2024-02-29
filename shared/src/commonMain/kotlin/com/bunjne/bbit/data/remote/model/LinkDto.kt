package com.bunjne.bbit.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDto(
    @SerialName("self")
    val self: SelfDto?,
    @SerialName("avatar")
    val avatar: SelfDto?,
    @SerialName("html")
    val html: SelfDto?
)

@Serializable
data class SelfDto(
    @SerialName("href")
    val href: String?
)
