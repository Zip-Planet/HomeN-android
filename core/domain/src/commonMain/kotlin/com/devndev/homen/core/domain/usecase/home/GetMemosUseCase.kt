package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Memo
import com.devndev.homen.core.domain.repository.HomeRepository

class GetMemosUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id: Int): ApiResult<List<Memo>> {
        return homeRepository.getMemos(id)
    }
}