package com.devndev.homen.core.data.model.reward.response

import com.devndev.homen.core.domain.model.reward.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RewardListResponse(
    @SerialName("my_point")
    val myPoint: Int,
    @SerialName("claimable_count")
    val claimableCount: Int,
    @SerialName("in_progress_count")
    val inProgressCount: Int,
    @SerialName("claimed_count")
    val claimedCount: Int,
    @SerialName("rewards")
    val rewards: List<RewardResponse>
)

@Serializable
data class RewardResponse(
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
    val createdAt: String
)

@Serializable
data class RewardCreatorResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int?
)

@Serializable
data class RewardClaimResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("claimed_at")
    val claimedAt: String
)

fun RewardListResponse.toDomainModel(): RewardList {
    return RewardList(
        myPoint = myPoint,
        claimableCount = claimableCount,
        inProgressCount = inProgressCount,
        claimedCount = claimedCount,
        rewards = rewards.map { it.toDomainModel() }
    )
}

fun RewardResponse.toDomainModel(): Reward {
    return Reward(
        id = id,
        name = name,
        goalPoint = goalPoint,
        status = RewardStatus.fromValue(status),
        remainingPoint = remainingPoint,
        createdBy = createdBy?.toDomainModel(),
        claim = claim?.toDomainModel(),
        createdAt = createdAt
    )
}

fun RewardCreatorResponse.toDomainModel(): RewardCreator {
    return RewardCreator(
        uid = uid,
        name = name,
        profileImage = profileImage
    )
}

fun RewardClaimResponse.toDomainModel(): RewardClaim {
    return RewardClaim(
        id = id,
        claimedAt = claimedAt
    )
}
