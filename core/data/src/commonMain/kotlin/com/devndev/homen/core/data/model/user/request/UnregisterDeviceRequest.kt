package com.devndev.homen.core.data.model.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnregisterDeviceRequest(
    @SerialName("registration_id")
    val registrationId: String
)
