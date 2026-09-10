package com.devndev.homen.core.data.service.board

import com.devndev.homen.core.data.model.board.response.BoardResponse

interface BoardService {
    companion object {
        const val GET_BOARD = "/homes/mine/board/"
    }

    suspend fun getBoard(): BoardResponse
}
