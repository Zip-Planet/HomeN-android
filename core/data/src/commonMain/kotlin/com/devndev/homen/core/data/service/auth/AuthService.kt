package com.devndev.homen.core.data.service.auth

import com.devndev.homen.core.data.model.auth.request.KakaoLoginRequest
import com.devndev.homen.core.data.model.auth.request.TokenRefreshRequest
import com.devndev.homen.core.data.model.auth.response.KakaoLoginResponse
import com.devndev.homen.core.data.model.auth.response.TokenRefreshResponse

interface AuthService {
    companion object {
        const val KAKAO_LOGIN = "/auth/kakao/"
        const val TOKEN_REFRESH = "/api/v1/auth/token/refresh/"
    }

    suspend fun kakaoLogin(kakaoLoginRequest: KakaoLoginRequest): KakaoLoginResponse
    suspend fun refreshToken(request: TokenRefreshRequest): TokenRefreshResponse
}
