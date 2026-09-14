package com.devndev.homen.core.data.service.board

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.board.request.CreateHelpRequest
import com.devndev.homen.core.data.model.board.response.BoardResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.first

class BoardServiceImpl(
    private val client: HttpClient,
    private val tokenRepository: TokenRepository
) : BoardService {
    override suspend fun getBoard(): BoardResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += BoardService.GET_BOARD
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun createHelp(itemId: Int, message: String?) {
        val accessToken = tokenRepository.getAccessToken().first()
        client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += BoardService.HELP
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(CreateHelpRequest(itemId = itemId, message = message))
        }
    }

    override suspend fun deleteHelp(helpRequestId: Int) {
        val accessToken = tokenRepository.getAccessToken().first()
        client.delete {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${BoardService.HELP}$helpRequestId/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }
    }
}
