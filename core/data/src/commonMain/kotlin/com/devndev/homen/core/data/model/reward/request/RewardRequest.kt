package com.devndev.homen.core.data.model.reward.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RewardRequest(
    @SerialName("name")
    val name: String,
    @SerialName("goal_point")
    val goalPoint: Int
)
