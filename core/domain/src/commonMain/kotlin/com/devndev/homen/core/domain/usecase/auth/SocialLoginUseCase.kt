package com.devndev.homen.core.domain.usecase.auth

import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.model.AppleUser
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.repository.AuthRepository

/**
 * 소셜 로그인을 수행하는 비즈니스 로직
 */
class SocialLoginUseCase(private val authRepository: AuthRepository) {
    suspend fun loginWithKakao(): SocialAuthResult<KakaoUser> {
        return authRepository.loginWithKakao()
    }

    suspend fun loginWithApple(): SocialAuthResult<AppleUser> {
        return authRepository.loginWithApple()
    }
}
