package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class DeleteMemoUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(choreId: Int, memoId: Int): ApiResult<Unit> {
        return homeRepository.deleteMemo(choreId, memoId)
    }
}