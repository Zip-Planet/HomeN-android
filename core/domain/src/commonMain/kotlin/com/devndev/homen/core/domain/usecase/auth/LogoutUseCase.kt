package com.devndev.homen.core.domain.usecase.auth

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.AuthRepository
import com.devndev.homen.core.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first

class LogoutUseCase(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): ApiResult<Unit> {
        val refreshToken = tokenRepository.getRefreshToken().first()
        return authRepository.logout(refreshToken!!)
    }
}
