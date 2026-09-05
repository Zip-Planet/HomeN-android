package com.devndev.homen.core.domain.usecase.reward

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.RewardRepository

class ClaimRewardUseCase(
    private val rewardRepository: RewardRepository
) {
    suspend operator fun invoke(rewardId: Int): ApiResult<Unit> {
        return rewardRepository.claimReward(rewardId)
    }
}
