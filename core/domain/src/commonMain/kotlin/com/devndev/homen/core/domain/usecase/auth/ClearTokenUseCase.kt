package com.devndev.homen.core.domain.usecase.auth

import com.devndev.homen.core.domain.repository.TokenRepository

class ClearTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke() {
        tokenRepository.clearTokens()
    }
}