package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.home.request.CreateChoreRequest
import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.request.EditChoreRequest
import com.devndev.homen.core.data.model.home.request.JoinHomeRequest
import com.devndev.homen.core.data.model.home.response.ChoreResponse
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHasHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHomeResponse
import com.devndev.homen.core.data.model.home.response.JoinHomeResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
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

    override suspend fun getHasHome(): GetHasHomeResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.GET_HAS_HOME
            }
            contentType(ContentType.Application.Json)
//            accessToken?.let {
//                header(HttpHeaders.Authorization, "Bearer $it")
//            }
        }.body()
    }

    override suspend fun getJoinHome(code: String): JoinHomeResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.GET_JOIN_HOME}$code"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun joinHome(joinHomeRequest: JoinHomeRequest) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.JOIN_HOME
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(joinHomeRequest)
        }.body()
    }

    override suspend fun createChore(createChoreRequest: CreateChoreRequest) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.CHORES
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(createChoreRequest)
        }.body()
    }

    override suspend fun getChores(): List<ChoreResponse> {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.CHORES
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun deleteChore(id: Int) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.delete {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$id/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun getChoreDetail(id: Int): ChoreResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$id/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun editChore(
        id: Int,
        editChoreRequest: EditChoreRequest
    ) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.patch {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$id/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(editChoreRequest)
        }.body()
    }
}
