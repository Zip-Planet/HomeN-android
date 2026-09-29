package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class LeaveHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): ApiResult<Unit> {
        return homeRepository.leaveHome()
    }
}
