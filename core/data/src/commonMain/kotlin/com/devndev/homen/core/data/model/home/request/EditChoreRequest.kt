package com.devndev.homen.core.data.model.home.request

import com.devndev.homen.core.domain.model.home.Chore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditChoreRequest(
    @SerialName("home_chore_id")
    val homeChoreId: Int,
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

fun Chore.toEditDataModel() = EditChoreRequest(
    homeChoreId = id!!,
    category = category,
    name = name,
    description = description,
    repeatDays = repeatDays,
    difficulty = difficulty.id
)
