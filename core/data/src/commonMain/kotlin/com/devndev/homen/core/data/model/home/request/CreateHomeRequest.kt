package com.devndev.homen.core.data.model.home.request

import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.Reward
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateHomeRequest(
    @SerialName("name")
    val name: String,
    @SerialName("image_id")
    val imageId: Int,
    @SerialName("chores")
    val chores: List<ChoreRequest>,
    @SerialName("rewards")
    val rewards: List<RewardRequest>
)

@Serializable
data class ChoreRequest(
    @SerialName("category")
    val category: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String = "",
    @SerialName("repeat_days")
    val repeatDays: List<Int>,
    @SerialName("difficulty")
    val difficulty: Int
)

@Serializable
data class RewardRequest(
    @SerialName("name")
    val name: String,
    @SerialName("goal_point")
    val goalPoint: Int
)


fun Chore.toDataModel() = ChoreRequest(
    category = category,
    name = name,
    description = description,
    repeatDays = repeatDays,
    difficulty = difficulty.id
)

fun Reward.toDataModel() = RewardRequest(
    name = name,
    goalPoint = goalPoint
)