package com.devndev.homen.core.data.model.home.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompleteChoreRequest(
    @SerialName("date")
    val date: String? = null
)
