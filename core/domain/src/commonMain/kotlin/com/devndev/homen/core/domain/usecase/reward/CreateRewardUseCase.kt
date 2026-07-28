package com.devndev.homen.core.domain.usecase.reward

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.RewardRepository

class CreateRewardUseCase(
    private val rewardRepository: RewardRepository
) {
    suspend operator fun invoke(name: String, goalPoint: Int): ApiResult<Unit> {
        return rewardRepository.createReward(name, goalPoint)
    }
}
