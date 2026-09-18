package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.board.response.toDomainModel
import com.devndev.homen.core.data.service.board.BoardService
import com.devndev.homen.core.domain.model.board.BoardCard
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.BoardRepository
import io.ktor.client.plugins.ResponseException

class BoardRepositoryImpl(
    private val boardService: BoardService
) : BoardRepository {
    override suspend fun getBoard(): ApiResult<List<BoardCard>> {
        return try {
            val response = boardService.getBoard()
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun createHelp(itemId: Int, message: String?): ApiResult<Unit> {
        return try {
            boardService.createHelp(itemId, message)
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun deleteHelp(helpRequestId: Int): ApiResult<Unit> {
        return try {
            boardService.deleteHelp(helpRequestId)
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun acceptHelp(helpRequestId: Int): ApiResult<Unit> {
        return try {
            boardService.acceptHelp(helpRequestId)
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}
