package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.repository.HomeRepository

class GetChoresUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): ApiResult<List<Chore>> {
        return homeRepository.getChores()
    }
}