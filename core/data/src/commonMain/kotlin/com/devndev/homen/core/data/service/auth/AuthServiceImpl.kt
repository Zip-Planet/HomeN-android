package com.devndev.homen.core.data.service.auth

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.auth.request.KakaoLoginRequest
import com.devndev.homen.core.data.model.auth.request.TokenRefreshRequest
import com.devndev.homen.core.data.model.auth.response.KakaoLoginResponse
import com.devndev.homen.core.data.model.auth.response.TokenRefreshResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

class AuthServiceImpl(
    private val client: HttpClient
) : AuthService {
    override suspend fun kakaoLogin(kakaoLoginRequest: KakaoLoginRequest): KakaoLoginResponse {
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += AuthService.KAKAO_LOGIN
            }
            contentType(ContentType.Application.Json)
            setBody(kakaoLoginRequest)
        }.body()
    }

    override suspend fun refreshToken(request: TokenRefreshRequest): TokenRefreshResponse {
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += AuthService.TOKEN_REFRESH
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
