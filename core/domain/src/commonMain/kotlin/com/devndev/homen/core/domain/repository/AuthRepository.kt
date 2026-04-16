package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.model.AppleUser
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.model.auth.AuthToken
import com.devndev.homen.core.domain.model.auth.SocialToken
import com.devndev.homen.core.domain.model.common.ApiResult

/**
 * 소셜 인증을 담당하는 저장소 인터페이스
 */
interface AuthRepository {
    suspend fun loginWithKakao(): SocialAuthResult<KakaoUser>
    suspend fun loginWithApple(): SocialAuthResult<AppleUser>
    suspend fun kakaoLoginToServer(socialToken: SocialToken): ApiResult<AuthToken>
}
