package com.devndev.homen.core.data.service.board

import com.devndev.homen.core.data.model.board.response.BoardResponse

interface BoardService {
    companion object {
        const val GET_BOARD = "/homes/mine/board/"
        const val CREATE_HELP = "/homes/mine/board/help/"
    }

    suspend fun getBoard(): BoardResponse

    suspend fun createHelp(itemId: Int, message: String?)
}
