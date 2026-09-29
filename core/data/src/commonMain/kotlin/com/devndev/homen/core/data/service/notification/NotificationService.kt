package com.devndev.homen.core.data.service.notification

import com.devndev.homen.core.data.model.notification.request.UpdateNotificationSettingRequest
import com.devndev.homen.core.data.model.notification.response.NotificationSettingResponse

interface NotificationService {
    companion object {
        const val NOTIFICATION_SETTINGS = "/notifications/settings/"
    }

    suspend fun getNotificationSetting(): NotificationSettingResponse
    suspend fun updateNotificationSetting(request: UpdateNotificationSettingRequest): NotificationSettingResponse
}
