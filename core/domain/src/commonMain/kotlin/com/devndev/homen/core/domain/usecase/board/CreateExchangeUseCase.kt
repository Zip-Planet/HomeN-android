package com.devndev.homen.core.domain.usecase.board

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.BoardRepository

class CreateExchangeUseCase(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(
        requesterItemId: Int,
        targetItemId: Int,
        message: String?
    ): ApiResult<Unit> {
        return boardRepository.createExchange(requesterItemId, targetItemId, message)
    }
}
