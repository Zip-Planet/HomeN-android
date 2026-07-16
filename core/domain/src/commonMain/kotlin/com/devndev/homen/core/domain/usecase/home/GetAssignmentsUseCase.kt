package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.repository.HomeRepository

class GetAssignmentsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(weekStart: String? = null): ApiResult<Assignment> {
        return homeRepository.getAssignments(weekStart)
    }
}
