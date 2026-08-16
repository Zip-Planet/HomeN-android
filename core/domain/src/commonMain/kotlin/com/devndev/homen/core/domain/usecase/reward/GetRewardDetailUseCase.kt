package com.devndev.homen.core.domain.usecase.reward

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.reward.RewardDetail
import com.devndev.homen.core.domain.repository.RewardRepository

class GetRewardDetailUseCase(
    private val rewardRepository: RewardRepository
) {
    suspend operator fun invoke(id: Int): ApiResult<RewardDetail> {
        return rewardRepository.getRewardDetail(id)
    }
}
