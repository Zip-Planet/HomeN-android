package com.devndev.homen.core.data.model.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceRequest(
    @SerialName("registration_id")
    val registrationId: String,
    @SerialName("platform")
    val platform: String, // "android" or "ios"
    @SerialName("kind")
    val kind: String = "token",
    @SerialName("app_version")
    val appVersion: String? = null
)
