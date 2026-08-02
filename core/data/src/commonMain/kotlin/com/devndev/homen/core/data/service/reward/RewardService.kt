package com.devndev.homen.core.data.service.reward

import com.devndev.homen.core.data.model.reward.request.RewardRequest
import com.devndev.homen.core.data.model.reward.response.RewardListResponse

interface RewardService {
    companion object {
        const val REWARDS = "/homes/mine/rewards/"
    }

    suspend fun getRewards(): RewardListResponse
    suspend fun createReward(rewardRequest: RewardRequest)
    suspend fun editReward(id: Int, rewardRequest: RewardRequest)
    suspend fun deleteReward(id: Int)
}
