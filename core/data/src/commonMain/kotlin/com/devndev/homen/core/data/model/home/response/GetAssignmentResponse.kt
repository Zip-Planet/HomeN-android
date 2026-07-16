package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.model.home.AssignmentAssignee
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.core.domain.model.home.MemberPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAssignmentResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("week_start")
    val weekStart: String, // YYYY-MM-DD
    @SerialName("status")
    val status: String, // proposed / confirmed / expired
    @SerialName("generated_at")
    val generatedAt: String, // ISO 8601
    @SerialName("confirmed_at")
    val confirmedAt: String?, // ISO 8601, 미확정 시 null
    @SerialName("items")
    val items: List<AssignmentItemResponse>,
    @SerialName("member_points")
    val memberPoints: List<MemberPointResponse>
)

@Serializable
data class AssignmentItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("home_chore_id")
    val homeChoreId: Int?,
    @SerialName("weekday")
    val weekday: Int,
    @SerialName("weekday_label")
    val weekdayLabel: String,
    @SerialName("chore_name")
    val choreName: String,
    @SerialName("category")
    val category: Int,
    @SerialName("category_label")
    val categoryLabel: String,
    @SerialName("difficulty")
    val difficulty: Int,
    @SerialName("difficulty_label")
    val difficultyLabel: String,
    @SerialName("point")
    val point: Int,
    @SerialName("assignee")
    val assignee: AssignmentAssigneeResponse?, // 탈퇴 시 null
    @SerialName("date")
    val date: String, // YYYY-MM-DD
    @SerialName("is_completed")
    val isCompleted: Boolean
)

@Serializable
data class AssignmentAssigneeResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int?
)

@Serializable
data class MemberPointResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("expected_point")
    val expectedPoint: Int
)

fun GetAssignmentResponse.toDomainModel(): Assignment {
    return Assignment(
        id = id,
        weekStart = weekStart,
        status = status,
        generatedAt = generatedAt,
        confirmedAt = confirmedAt,
        items = items.map { it.toDomainModel() },
        memberPoints = memberPoints.map { it.toDomainModel() }
    )
}

fun AssignmentItemResponse.toDomainModel(): AssignmentItem {
    return AssignmentItem(
        id = id,
        homeChoreId = homeChoreId,
        weekday = weekday,
        weekdayLabel = weekdayLabel,
        choreName = choreName,
        category = category,
        categoryLabel = categoryLabel,
        difficulty = difficulty,
        difficultyLabel = difficultyLabel,
        point = point,
        assignee = assignee?.toDomainModel(),
        date = date,
        isCompleted = isCompleted
    )
}

fun AssignmentAssigneeResponse.toDomainModel(): AssignmentAssignee {
    return AssignmentAssignee(
        uid = uid,
        name = name,
        profileImage = profileImage
    )
}

fun MemberPointResponse.toDomainModel(): MemberPoint {
    return MemberPoint(
        uid = uid,
        name = name,
        expectedPoint = expectedPoint
    )
}
