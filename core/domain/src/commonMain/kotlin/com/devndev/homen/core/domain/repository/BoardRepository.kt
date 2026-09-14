package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.board.BoardCard
import com.devndev.homen.core.domain.model.common.ApiResult

interface BoardRepository {
    suspend fun getBoard(): ApiResult<List<BoardCard>>
    suspend fun createHelp(itemId: Int, message: String?): ApiResult<Unit>
    suspend fun deleteHelp(helpRequestId: Int): ApiResult<Unit>
}
