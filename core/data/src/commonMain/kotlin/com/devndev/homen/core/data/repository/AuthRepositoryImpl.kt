package com.devndev.homen.core.data.repository

import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.AppleUser
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val kakaoAuthenticator: SocialAuthenticator<KakaoUser>,
    private val appleAuthenticator: SocialAuthenticator<AppleUser>
) : AuthRepository {
    override suspend fun loginWithKakao(): SocialAuthResult<KakaoUser> {
        return kakaoAuthenticator.authenticate()
    }

    override suspend fun loginWithApple(): SocialAuthResult<AppleUser> {
        return appleAuthenticator.authenticate()
    }
}
