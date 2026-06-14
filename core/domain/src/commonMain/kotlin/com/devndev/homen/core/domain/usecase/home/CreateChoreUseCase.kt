package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.repository.HomeRepository

class CreateChoreUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(chores: List<Chore>): ApiResult<Unit> {
        return homeRepository.createChore(chores)
    }
}