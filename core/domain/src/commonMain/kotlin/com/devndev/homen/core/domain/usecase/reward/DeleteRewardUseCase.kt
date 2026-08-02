package com.devndev.homen.core.domain.usecase.reward

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.RewardRepository

class DeleteRewardUseCase(
    private val rewardRepository: RewardRepository
) {
    suspend operator fun invoke(id: Int): ApiResult<Unit> {
        return rewardRepository.deleteReward(id)
    }
}
