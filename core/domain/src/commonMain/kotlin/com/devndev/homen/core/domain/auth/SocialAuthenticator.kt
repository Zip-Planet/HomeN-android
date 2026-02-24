package com.devndev.homen.core.domain.auth

/**
 * 실제 인증 동작을 수행
 */
interface SocialAuthenticator<T> {
    suspend fun authenticate(): SocialAuthResult<T>
}
