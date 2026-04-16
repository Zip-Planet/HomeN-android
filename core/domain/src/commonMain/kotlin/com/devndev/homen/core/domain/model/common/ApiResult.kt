package com.devndev.homen.core.domain.model.common

/**
 * API 통신 결과를 담는 공통 Result 클래스
 */
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val code: Int? = null, val message: String? = null) : ApiResult<Nothing>
    data object NetworkError : ApiResult<Nothing>
}
