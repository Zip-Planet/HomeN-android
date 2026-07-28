package com.devndev.homen.core.domain.model.reward

data class Reward(
    val id: Int,
    val name: String,
    val goalPoint: Int,
    val status: RewardStatus,
    val remainingPoint: Int,
    val createdBy: RewardCreator?,
    val claim: RewardClaim?,
    val createdAt: String
)

enum class RewardStatus(val value: String) {
    CLAIMED("claimed"),
    CLAIMABLE("claimable"),
    IN_PROGRESS("in_progress"),
    NONE("none");

    companion object {
        fun fromValue(value: String): RewardStatus {
            return entries.find { it.value == value } ?: NONE
        }
    }
}

data class RewardCreator(
    val uid: String,
    val name: String,
    val profileImage: Int?
)

data class RewardClaim(
    val id: Int,
    val claimedAt: String
)

data class RewardList(
    val myPoint: Int,
    val claimableCount: Int,
    val inProgressCount: Int,
    val claimedCount: Int,
    val rewards: List<Reward>
)
