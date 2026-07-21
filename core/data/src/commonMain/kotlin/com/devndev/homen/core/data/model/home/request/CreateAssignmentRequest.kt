package com.devndev.homen.core.data.model.home.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAssignmentRequest(
    @SerialName("week_start")
    val weekStart: String? = null
)
