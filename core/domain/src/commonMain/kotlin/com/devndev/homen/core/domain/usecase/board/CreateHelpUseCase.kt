package com.devndev.homen.core.domain.usecase.board

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.BoardRepository

class CreateHelpUseCase(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(itemId: Int, message: String?): ApiResult<Unit> {
        return boardRepository.createHelp(itemId, message)
    }
}
