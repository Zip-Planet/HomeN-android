package com.devndev.homen.core.domain.usecase.board

import com.devndev.homen.core.domain.model.board.BoardCard
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.BoardRepository

class GetBoardUseCase(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(): ApiResult<List<BoardCard>> {
        return boardRepository.getBoard()
    }
}
