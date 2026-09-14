package com.devndev.homen.core.data.model.board.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateHelpRequest(
    @SerialName("item_id")
    val itemId: Int,
    @SerialName("message")
    val message: String? = null
)
