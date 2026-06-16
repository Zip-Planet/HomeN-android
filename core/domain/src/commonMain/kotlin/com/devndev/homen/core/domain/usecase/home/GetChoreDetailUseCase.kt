package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.ChoreDetail
import com.devndev.homen.core.domain.repository.HomeRepository

class GetChoreDetailUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id: Int): ApiResult<ChoreDetail> {
        return homeRepository.getChoreDetail(id)
    }
}