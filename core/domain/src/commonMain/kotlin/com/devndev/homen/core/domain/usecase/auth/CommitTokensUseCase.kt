package com.devndev.homen.core.domain.usecase.auth

import com.devndev.homen.core.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first

/**
 * 임시(메모리) 저장소에 있는 토큰을 영구(DataStore) 저장소로 확정(Commit)
 */
class CommitTokensUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke() {
        val access = tokenRepository.getAccessToken().first()
        val refresh = tokenRepository.getRefreshToken().first()

        if (!access.isNullOrBlank() && !refresh.isNullOrBlank()) {
            tokenRepository.saveTokens(access, refresh, isPermanent = true)
        }
    }
}
