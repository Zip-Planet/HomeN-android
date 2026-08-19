package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.ConfirmAssignment
import com.devndev.homen.core.domain.repository.HomeRepository

class ConfirmAssignmentUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(assignmentId: Int, acknowledged: Boolean): ApiResult<ConfirmAssignment> {
        return homeRepository.confirmAssignment(assignmentId, acknowledged)
    }
}
