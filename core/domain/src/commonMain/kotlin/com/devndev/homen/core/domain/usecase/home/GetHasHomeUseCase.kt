package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class GetHasHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): ApiResult<Boolean> {
        return homeRepository.getHasHome()
    }
}