package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class TransferAdminUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(userId: String): ApiResult<Unit> {
        return homeRepository.transferAdmin(userId)
    }
}
