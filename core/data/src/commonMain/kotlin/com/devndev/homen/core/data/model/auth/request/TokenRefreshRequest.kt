package com.devndev.homen.core.data.model.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    @SerialName("refresh")
    val refresh: String
)
