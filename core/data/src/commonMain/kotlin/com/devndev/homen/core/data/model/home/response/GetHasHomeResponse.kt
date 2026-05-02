package com.devndev.homen.core.data.model.home.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHasHomeResponse(
    @SerialName("has_home")
    val hasHome: Boolean
)
