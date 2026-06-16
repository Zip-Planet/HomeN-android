package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class CreateMemoUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id: Int, content: String): ApiResult<Unit> {
        return homeRepository.createMemos(id, content)
    }
}