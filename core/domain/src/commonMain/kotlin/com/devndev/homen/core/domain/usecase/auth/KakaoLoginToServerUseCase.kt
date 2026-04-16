package com.devndev.homen.core.domain.usecase.auth

import com.devndev.homen.core.domain.model.auth.AuthToken
import com.devndev.homen.core.domain.model.auth.SocialToken
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.AuthRepository

class KakaoLoginToServerUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(socialToken: SocialToken): ApiResult<AuthToken> {
        return authRepository.kakaoLoginToServer(socialToken)
    }
}
