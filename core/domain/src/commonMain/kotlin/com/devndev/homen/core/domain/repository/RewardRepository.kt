package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.reward.RewardList

interface RewardRepository {
    suspend fun getRewards(): ApiResult<RewardList>
    suspend fun createReward(name: String, goalPoint: Int): ApiResult<Unit>
    suspend fun editReward(id: Int, name: String, goalPoint: Int): ApiResult<Unit>
    suspend fun deleteReward(id: Int): ApiResult<Unit>
}
