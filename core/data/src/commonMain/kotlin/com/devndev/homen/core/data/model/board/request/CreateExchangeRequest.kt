package com.devndev.homen.core.data.model.board.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateExchangeRequest(
    @SerialName("requester_item_id")
    val requesterItemId: Int,
    @SerialName("target_item_id")
    val targetItemId: Int,
    @SerialName("message")
    val message: String? = null
)
