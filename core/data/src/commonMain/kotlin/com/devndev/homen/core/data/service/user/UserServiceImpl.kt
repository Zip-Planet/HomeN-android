package com.devndev.homen.core.data.service.user

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.user.request.UpdateProfileRequest
import com.devndev.homen.core.data.model.user.response.GetMyInfoResponse
import com.devndev.homen.core.data.model.user.response.UpdateProfileResponse
import com.devndev.homen.core.data.model.user.response.ValidateNicknameResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.first

class UserServiceImpl(
    private val client: HttpClient,
    private val tokenRepository: TokenRepository
): UserService {
    override suspend fun getMyInfo(): GetMyInfoResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += UserService.GET_MY_INFO
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun updateProfile(request: UpdateProfileRequest): UpdateProfileResponse {
        return client.patch {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += UserService.UPDATE_PROFILE
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun validateNickname(nickname: String): ValidateNicknameResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += "${UserService.VALIDATE_NICKNAME}$nickname/"
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }
}