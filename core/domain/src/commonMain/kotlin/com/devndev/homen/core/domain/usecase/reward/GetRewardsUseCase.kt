package com.devndev.homen.core.domain.usecase.reward

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.reward.RewardList
import com.devndev.homen.core.domain.repository.RewardRepository

class GetRewardsUseCase(
    private val rewardRepository: RewardRepository
) {
    suspend operator fun invoke(): ApiResult<RewardList> {
        return rewardRepository.getRewards()
    }
}
