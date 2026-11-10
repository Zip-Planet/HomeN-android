package com.devndev.homen.core.domain.util

interface FcmTokenProvider {
    suspend fun getFcmToken(): String?
    fun getPlatform(): String
}
