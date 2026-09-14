package com.devndev.homen.core.data.model.board.response

import com.devndev.homen.core.domain.model.board.BoardCard
import com.devndev.homen.core.domain.model.board.BoardChoreItem
import com.devndev.homen.core.domain.model.board.BoardMember
import com.devndev.homen.core.domain.model.board.BoardPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardResponse(
    @SerialName("cards")
    val cards: List<BoardCardResponse>
)

@Serializable
data class BoardCardResponse(
    @SerialName("type")
    val type: String,
    @SerialName("id")
    val id: Int,
    @SerialName("week_start")
    val weekStart: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("kind")
    val kind: String? = null,
    @SerialName("kind_label")
    val kindLabel: String? = null,
    @SerialName("payload")
    val payload: BoardPayloadResponse? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("requester")
    val requester: BoardMemberResponse? = null,
    @SerialName("accepted_by")
    val acceptedBy: BoardMemberResponse? = null,
    @SerialName("responded_by")
    val respondedBy: BoardMemberResponse? = null,
    @SerialName("item")
    val item: BoardChoreItemResponse? = null,
    @SerialName("requester_item")
    val requesterItem: BoardChoreItemResponse? = null,
    @SerialName("target_item")
    val targetItem: BoardChoreItemResponse? = null
)

@Serializable
data class BoardPayloadResponse(
    // Reward
    @SerialName("claimed_by") val claimedBy: BoardMemberResponse? = null,
    @SerialName("goal_point") val goalPoint: Int? = null,
    @SerialName("reward_name") val rewardName: String? = null,
    @SerialName("claimed_by_point") val claimedByPoint: Int? = null,

    // Assignment
    @SerialName("week_start") val weekStart: String? = null,
    @SerialName("total_count") val totalCount: Int? = null,

    // Others (Report, etc)
    @SerialName("completion_rate") val completionRate: Double? = null,
    @SerialName("mvp_name") val mvpName: String? = null,
    @SerialName("mvp_point") val mvpPoint: Int? = null,
    @SerialName("reward_point") val rewardPoint: Int? = null,
    @SerialName("total_chore_count") val totalChoreCount: Int? = null,
    @SerialName("completed_chore_count") val completedChoreCount: Int? = null,
    @SerialName("content") val content: String? = null
)

@Serializable
data class BoardMemberResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int?
)

@Serializable
data class BoardChoreItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("chore_name")
    val choreName: String,
    @SerialName("weekday")
    val weekday: Int,
    @SerialName("weekday_label")
    val weekdayLabel: String,
    @SerialName("difficulty")
    val difficulty: Int,
    @SerialName("point")
    val point: Int,
    @SerialName("date")
    val date: String,
    @SerialName("assignee")
    val assignee: BoardMemberResponse?
)

fun BoardResponse.toDomainModel(): List<BoardCard> {
    return cards.map { it.toDomainModel() }
}

fun BoardCardResponse.toDomainModel(): BoardCard {
    return BoardCard(
        type = type,
        id = id,
        weekStart = weekStart,
        createdAt = createdAt,
        kind = kind,
        kindLabel = kindLabel,
        payload = payload?.toDomainModel(kind),
        status = status,
        message = message,
        requester = requester?.toDomainModel(),
        acceptedBy = acceptedBy?.toDomainModel(),
        respondedBy = respondedBy?.toDomainModel(),
        item = item?.toDomainModel(),
        requesterItem = requesterItem?.toDomainModel(),
        targetItem = targetItem?.toDomainModel()
    )
}

fun BoardPayloadResponse.toDomainModel(kind: String?): BoardPayload {
    return when (kind) {
        "reward_achieved" -> BoardPayload.Reward(
            claimedBy = claimedBy?.toDomainModel() ?: BoardMember("", "", null),
            goalPoint = goalPoint ?: 0,
            rewardName = rewardName ?: "",
            claimedByPoint = claimedByPoint ?: 0
        )
        "assignment_created", "assignment_confirmed" -> BoardPayload.Assignment(
            weekStart = weekStart ?: "",
            totalCount = totalCount ?: 0
        )
        "weekly_report" -> BoardPayload.Report(
            mvpName = mvpName ?: "",
            mvpPoint = mvpPoint ?: 0,
            totalChoreCount = totalChoreCount ?: 0,
            completedChoreCount = completedChoreCount ?: 0,
            completionRate = completionRate ?: 0.0
        )
        else -> BoardPayload.Default(content = content)
    }
}

fun BoardMemberResponse.toDomainModel(): BoardMember {
    return BoardMember(
        uid = uid,
        name = name,
        profileImage = profileImage
    )
}

fun BoardChoreItemResponse.toDomainModel(): BoardChoreItem {
    return BoardChoreItem(
        id = id,
        choreName = choreName,
        weekday = weekday,
        weekdayLabel = weekdayLabel,
        difficulty = difficulty,
        point = point,
        date = date,
        assignee = assignee?.toDomainModel()
    )
}
