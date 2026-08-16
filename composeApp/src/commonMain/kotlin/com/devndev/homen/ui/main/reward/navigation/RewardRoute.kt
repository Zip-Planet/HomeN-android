package com.devndev.homen.ui.main.reward.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RewardRoute {
    @Serializable
    data class EditReward(val rewardId: Int?, val reward: String?, val point: String?, val isEdit: Boolean)

    @Serializable
    data class RewardDetail(val rewardId: Int)
}