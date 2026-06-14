package com.devndev.homen.core.data.model.home.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChoreRequest(
    @SerialName("starter_pack_id")
    val starterPackId: Int? = null,
    @SerialName("chores")
    val chores: List<ChoreRequest>
)