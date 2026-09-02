package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class CompleteChoreUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(homeChoreId: Int, date: String? = null): ApiResult<Unit> {
        return homeRepository.completeChore(homeChoreId, date)
    }
}
