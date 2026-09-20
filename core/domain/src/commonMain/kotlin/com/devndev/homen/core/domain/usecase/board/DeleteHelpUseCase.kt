package com.devndev.homen.core.domain.usecase.board

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.BoardRepository

class DeleteHelpUseCase(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(helpRequestId: Int): ApiResult<Unit> {
        return boardRepository.deleteHelp(helpRequestId)
    }
}
