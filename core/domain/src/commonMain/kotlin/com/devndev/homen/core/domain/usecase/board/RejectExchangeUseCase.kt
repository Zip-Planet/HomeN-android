package com.devndev.homen.core.domain.usecase.board

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.BoardRepository

class RejectExchangeUseCase(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(swapId: Int): ApiResult<Unit> {
        return boardRepository.rejectExchange(swapId)
    }
}
