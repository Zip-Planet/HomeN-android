package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.home.Chore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ChoreResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("category")
    val category: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("repeat_days")
    val repeatDays: List<Int>,
    @SerialName("difficulty")
    val difficulty: Int
)

fun List<ChoreResponse>.toDomainModel(): List<Chore> {
    return this.map { it.toDomainModel() }
}

fun ChoreResponse.toDomainModel(): Chore {
    return Chore(
        id = id,
        category = category,
        name = name,
        description = description,
        repeatDays = repeatDays,
        difficulty = ChoreDifficulty.fromId(difficulty)
    )
}