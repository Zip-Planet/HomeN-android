package com.devndev.homen.core.domain.usecase.reward

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.RewardRepository

class EditRewardUseCase(
    private val rewardRepository: RewardRepository
) {
    suspend operator fun invoke(id: Int, name: String, goalPoint: Int): ApiResult<Unit> {
        return rewardRepository.editReward(id, name, goalPoint)
    }
}
