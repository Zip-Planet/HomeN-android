package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.reward.request.RewardRequest
import com.devndev.homen.core.data.model.reward.response.toDomainModel
import com.devndev.homen.core.data.service.reward.RewardService
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.reward.RewardList
import com.devndev.homen.core.domain.repository.RewardRepository
import io.ktor.client.plugins.ResponseException

class RewardRepositoryImpl(
    private val rewardService: RewardService
) : RewardRepository {
    override suspend fun getRewards(): ApiResult<RewardList> {
        return try {
            val response = rewardService.getRewards()
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun createReward(name: String, goalPoint: Int): ApiResult<Unit> {
        return try {
            rewardService.createReward(RewardRequest(name, goalPoint))
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun editReward(id: Int, name: String, goalPoint: Int): ApiResult<Unit> {
        return try {
            rewardService.editReward(id, RewardRequest(name, goalPoint))
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}
