package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHomeResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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

class HomeServiceImpl(
    private val client: HttpClient,
    private val tokenRepository: TokenRepository
) : HomeService {
    override suspend fun createHome(createHomeRequest: CreateHomeRequest): CreateHomeResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.CREATE_HOME
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(createHomeRequest)
        }.body()
    }

    override suspend fun getHome(): GetHomeResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.GET_HOME
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }
}
