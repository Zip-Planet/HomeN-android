package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.repository.HomeRepository

class EditChoreUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(chore: Chore): ApiResult<Unit> {
        return homeRepository.editChore(chore)
    }
}