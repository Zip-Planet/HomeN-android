package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository

class EditMemoUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(choreId: Int, memoId: Int, content: String): ApiResult<Unit> {
        return homeRepository.editMemo(choreId = choreId, memoId = memoId, content = content)
    }
}