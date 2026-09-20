package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.board.BoardCard
import com.devndev.homen.core.domain.model.common.ApiResult

interface BoardRepository {
    suspend fun getBoard(): ApiResult<List<BoardCard>>
    suspend fun createHelp(itemId: Int, message: String?): ApiResult<Unit>
    suspend fun deleteHelp(helpRequestId: Int): ApiResult<Unit>
    suspend fun acceptHelp(helpRequestId: Int): ApiResult<Unit>
    suspend fun createExchange(requesterItemId: Int, targetItemId: Int, message: String?): ApiResult<Unit>
    suspend fun deleteExchange(swapId: Int): ApiResult<Unit>
    suspend fun acceptExchange(swapId: Int): ApiResult<Unit>
    suspend fun rejectExchange(swapId: Int): ApiResult<Unit>
}
