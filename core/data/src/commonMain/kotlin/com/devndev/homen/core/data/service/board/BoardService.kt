package com.devndev.homen.core.data.service.board

import com.devndev.homen.core.data.model.board.response.BoardResponse

interface BoardService {
    companion object {
        const val GET_BOARD = "/homes/mine/board/"
        const val HELP = "/homes/mine/board/help/"
        const val SWAP = "/homes/mine/board/swap/"
    }

    suspend fun getBoard(): BoardResponse

    suspend fun createHelp(itemId: Int, message: String?)

    suspend fun deleteHelp(helpRequestId: Int)

    suspend fun acceptHelp(helpRequestId: Int)

    suspend fun createExchange(requesterItemId: Int, targetItemId: Int, message: String?)

    suspend fun deleteExchange(swapId: Int)

    suspend fun acceptExchange(swapId: Int)

    suspend fun rejectExchange(swapId: Int)
}
