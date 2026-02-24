package com.devndev.homen.core.domain.auth

/**
 * 소셜 로그인 결과 상태를 관리
 */
sealed interface SocialAuthResult<out T> {
    data class Success<T>(val data: T) : SocialAuthResult<T>
    data object UserCancelled : SocialAuthResult<Nothing>
    data object Error : SocialAuthResult<Nothing>
}
