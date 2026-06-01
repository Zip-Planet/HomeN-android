package com.devndev.homen.core.data.model.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidateNicknameResponse(
    @SerialName("is_available")
    val isAvailable: Boolean
)
