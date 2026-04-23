package com.devndev.homen.core.domain.usecase.splash

import com.devndev.homen.core.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first

class CheckTokenUseCase(
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke(): Boolean {
        return if (tokenRepository.getAccessToken().first().isNullOrEmpty()) {
            false
        } else {
            true
        }
    }
}
