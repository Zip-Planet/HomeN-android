package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.notification.NotificationList
import com.devndev.homen.core.domain.model.notification.NotificationSetting

interface NotificationRepository {
    suspend fun getNotificationSetting(): ApiResult<NotificationSetting>
    suspend fun updateNotificationSetting(
        pushEnabled: Boolean? = null,
        homeMember: Boolean? = null,
        assignment: Boolean? = null,
        board: Boolean? = null,
        reward: Boolean? = null,
        report: Boolean? = null
    ): ApiResult<NotificationSetting>

    suspend fun getNotifications(category: String? = null): ApiResult<NotificationList>
    suspend fun readNotification(notificationId: Int): ApiResult<Unit>
}
