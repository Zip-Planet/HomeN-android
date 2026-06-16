package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.ChoreDetail
import com.devndev.homen.core.domain.model.home.CompletedBy
import com.devndev.homen.core.domain.model.home.WeeklyProgress
import com.devndev.homen.core.domain.model.home.WeeklyProgressStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChoreDetailResponse(
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
    val difficulty: Int,
    @SerialName("weekly_progress")
    val weeklyProgress: List<WeeklyProgressResponse>
)

@Serializable
data class WeeklyProgressResponse(
    @SerialName("weekday")
    val weekDay: Int,
    @SerialName("label")
    val label: String,
    @SerialName("status")
    val status: String,
    @SerialName("completed_by")
    val completedBy: CompletedByResponse?
)

@Serializable
data class CompletedByResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int
)


fun ChoreDetailResponse.toDomainModel(): ChoreDetail {
    return ChoreDetail(
        chore = Chore(
            id = id,
            category = category,
            name = name,
            description = description,
            repeatDays = repeatDays,
            difficulty = ChoreDifficulty.fromId(difficulty)
        ),
        weeklyProgress = weeklyProgress.map { it.toDomainModel() }
    )
}

fun WeeklyProgressResponse.toDomainModel(): WeeklyProgress {
    return WeeklyProgress(
        weekDay = weekDay,
        label = label,
        status = WeeklyProgressStatus.fromStatus(status),
        completedBy = completedBy?.toDomainModel()
    )
}

fun CompletedByResponse.toDomainModel(): CompletedBy {
    return CompletedBy(
        uid = uid,
        name = name,
        profileImage = profileImage
    )
}