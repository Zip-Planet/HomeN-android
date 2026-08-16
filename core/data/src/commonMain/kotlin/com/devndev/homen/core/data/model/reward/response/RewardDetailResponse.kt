package com.devndev.homen.core.data.model.reward.response

import com.devndev.homen.core.domain.model.reward.MemberProgress
import com.devndev.homen.core.domain.model.reward.RewardDetail
import com.devndev.homen.core.domain.model.reward.RewardStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RewardDetailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("goal_point")
    val goalPoint: Int,
    @SerialName("status")
    val status: String,
    @SerialName("remaining_point")
    val remainingPoint: Int,
    @SerialName("created_by")
    val createdBy: RewardCreatorResponse?,
    @SerialName("claim")
    val claim: RewardClaimResponse?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("member_progress")
    val memberProgress: List<MemberProgressResponse>
)

@Serializable
data class MemberProgressResponse(
    @SerialName("rank")
    val rank: Int,
    @SerialName("point")
    val point: Int,
    @SerialName("achievement_rate")
    val achievementRate: Int
)

fun RewardDetailResponse.toDomainModel(): RewardDetail {
    return RewardDetail(
        id = id,
        name = name,
        goalPoint = goalPoint,
        status = RewardStatus.fromValue(status),
        remainingPoint = remainingPoint,
        createdBy = createdBy?.toDomainModel(),
        claim = claim?.toDomainModel(),
        createdAt = createdAt,
        memberProgress = memberProgress.map { it.toDomainModel() }
    )
}

fun MemberProgressResponse.toDomainModel(): MemberProgress {
    return MemberProgress(
        rank = rank,
        point = point,
        achievementRate = achievementRate
    )
}
