package com.devndev.homen.core.data.model.home.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferAdminRequest(
    @SerialName("user_id")
    val userId: String
)
