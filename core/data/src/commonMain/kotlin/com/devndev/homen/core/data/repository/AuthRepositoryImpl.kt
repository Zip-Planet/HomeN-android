package com.devndev.homen.core.data.repository

import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val kakaoAuthenticator: SocialAuthenticator<KakaoUser>
) : AuthRepository {
    override suspend fun loginWithKakao(): SocialAuthResult<KakaoUser> {
        return kakaoAuthenticator.authenticate()
    }
}
