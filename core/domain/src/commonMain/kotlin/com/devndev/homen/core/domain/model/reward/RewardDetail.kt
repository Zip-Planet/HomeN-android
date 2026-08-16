package com.devndev.homen.core.domain.model.reward

data class RewardDetail(
    val id: Int,
    val name: String,
    val goalPoint: Int,
    val status: RewardStatus,
    val remainingPoint: Int,
    val createdBy: RewardCreator?,
    val claim: RewardClaim?,
    val createdAt: String,
    val memberProgress: List<MemberProgress>
)

data class MemberProgress(
    val rank: Int,
    val point: Int,
    val achievementRate: Int
)
