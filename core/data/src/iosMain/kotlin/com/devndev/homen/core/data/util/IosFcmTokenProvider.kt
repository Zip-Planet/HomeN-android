package com.devndev.homen.core.data.util

import com.devndev.homen.core.common.util.Logger
import com.devndev.homen.core.domain.util.FcmTokenProvider

class IosFcmTokenProvider : FcmTokenProvider {
    override suspend fun getFcmToken(): String? {
        return try {
            Logger.d("SHKIM", "getFcmToken : ios")

            // iOS FCM token retrieval via Firebase SDK / APNs
            null
        } catch (e: Exception) {
            null
        }
    }

    override fun getPlatform(): String = "ios"
}
