package com.devndev.homen.core.data.model.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshResponse(
    @SerialName("access")
    val access: String
)