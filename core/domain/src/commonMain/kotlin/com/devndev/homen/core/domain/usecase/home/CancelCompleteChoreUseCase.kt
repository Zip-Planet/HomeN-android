package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class CancelCompleteChoreUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(homeChoreId: Int, completionDate: String): ApiResult<Unit> {
        return homeRepository.cancelCompleteChore(homeChoreId, completionDate)
    }
}
