package com.devndev.homen.core.domain.usecase.auth

import com.devndev.homen.core.domain.repository.TokenRepository

class SaveTokensUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String, isPermanent: Boolean = true) {
        tokenRepository.saveTokens(accessToken, refreshToken, isPermanent)
    }
}
