package com.devndev.homen.core.data.model.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int
)
