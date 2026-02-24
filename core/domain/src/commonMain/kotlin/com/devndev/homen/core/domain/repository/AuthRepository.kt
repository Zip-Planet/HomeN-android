package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.model.KakaoUser

interface AuthRepository {
    suspend fun loginWithKakao(): SocialAuthResult<KakaoUser>
}
