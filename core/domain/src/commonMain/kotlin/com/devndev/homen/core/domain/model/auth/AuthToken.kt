package com.devndev.homen.core.domain.model.auth

/**
 * 서버에서 발급한 인증 토큰 정보 (출력 모델)
 */
data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val isProfileSet: Boolean
)
