package com.devndev.homen.core.data.service.auth

import com.devndev.homen.core.data.model.auth.request.KakaoLoginRequest
import com.devndev.homen.core.data.model.auth.response.KakaoLoginResponse

interface AuthService {
    companion object {
        const val KAKAO_LOGIN = "/auth/kakao/"
    }

    suspend fun kakaoLogin(kakaoLoginRequest: KakaoLoginRequest): KakaoLoginResponse
}