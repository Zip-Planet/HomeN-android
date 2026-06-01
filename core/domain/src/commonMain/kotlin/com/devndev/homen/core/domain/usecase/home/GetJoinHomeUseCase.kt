package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class GetJoinHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(code: String): ApiResult<Unit> {
        return homeRepository.getJoinHome(code)
    }
}