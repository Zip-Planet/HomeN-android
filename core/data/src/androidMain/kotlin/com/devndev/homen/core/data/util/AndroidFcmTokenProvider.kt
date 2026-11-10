package com.devndev.homen.core.data.util

import com.devndev.homen.core.common.util.Logger
import com.devndev.homen.core.domain.util.FcmTokenProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class AndroidFcmTokenProvider : FcmTokenProvider {
    override suspend fun getFcmToken(): String? {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            Logger.d("SHKIM", "getFcmToken success: $token")

            token
        } catch (e: Exception) {
            Logger.d("SHKIM", "getFcmToken: ${e.message}")
            null
        }
    }

    override fun getPlatform(): String = "android"
}
