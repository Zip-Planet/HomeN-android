package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.request.toDataModel
import com.devndev.homen.core.data.model.home.response.toDomainModel
import com.devndev.homen.core.data.service.home.HomeService
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.repository.HomeRepository
import io.ktor.client.plugins.ResponseException

class HomeRepositoryImpl(
    private val homeService: HomeService
): HomeRepository {
    override suspend fun createHome(createHome: CreateHome): ApiResult<HomeResponseDomainModel> {
        return try {
            val response = homeService.createHome(
                CreateHomeRequest(
                    name = createHome.name,
                    imageId = createHome.imageId,
                    chores = createHome.chores.map { it.toDataModel() },
                    rewards = createHome.rewards.map { it.toDataModel() }
                )
            )
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}