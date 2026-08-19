package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.home.request.ConfirmAssignmentRequest
import com.devndev.homen.core.data.model.home.request.CreateAssignmentRequest
import com.devndev.homen.core.data.model.home.request.CreateChoreRequest
import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.request.EditChoreRequest
import com.devndev.homen.core.data.model.home.request.JoinHomeRequest
import com.devndev.homen.core.data.model.home.request.MemoRequest
import com.devndev.homen.core.data.model.home.response.ChoreDetailResponse
import com.devndev.homen.core.data.model.home.response.ChoreResponse
import com.devndev.homen.core.data.model.home.response.ConfirmAssignmentResponse
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import com.devndev.homen.core.data.model.home.response.GetAssignmentResponse
import com.devndev.homen.core.data.model.home.response.GetHasHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHomeResponse
import com.devndev.homen.core.data.model.home.response.GetMemoResponse
import com.devndev.homen.core.data.model.home.response.JoinHomeResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
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

    override suspend fun getChoreDetail(id: Int): ChoreDetailResponse {
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

    override suspend fun getAssignments(weekStart: String?): GetAssignmentResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.ASSIGNMENT
                weekStart?.let { parameter("week_start", it) }
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun createAssignment(createAssignmentRequest: CreateAssignmentRequest): GetAssignmentResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.ASSIGNMENT
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(createAssignmentRequest)
        }.body()
    }

    override suspend fun confirmAssignment(
        assignmentId: Int,
        confirmAssignmentRequest: ConfirmAssignmentRequest
    ): ConfirmAssignmentResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.ASSIGNMENT}$assignmentId/confirm/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(confirmAssignmentRequest)
        }.body()
    }

    override suspend fun regenerateAssignment(assignmentId: Int): GetAssignmentResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.ASSIGNMENT}$assignmentId/regenerate/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun getMemos(id: Int): List<GetMemoResponse> {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$id/notes/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun createMemo(id: Int, createMemoRequest: MemoRequest) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$id/notes/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(createMemoRequest)
        }.body()
    }

    override suspend fun editMemo(choreId: Int, memoId: Int, editMemoRequest: MemoRequest) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.patch {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$choreId/notes/$memoId/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(editMemoRequest)
        }.body()
    }

    override suspend fun deleteMemo(choreId: Int, memoId: Int) {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.delete {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${HomeService.CHORES}$choreId/notes/$memoId/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }
}
