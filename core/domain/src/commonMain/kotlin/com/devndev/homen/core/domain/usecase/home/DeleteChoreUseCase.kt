package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class DeleteChoreUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id: Int): ApiResult<Unit> {
        return homeRepository.deleteChore(id)
    }
}
