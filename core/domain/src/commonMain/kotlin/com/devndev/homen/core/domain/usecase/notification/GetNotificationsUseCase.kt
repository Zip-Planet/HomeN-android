package com.devndev.homen.core.domain.usecase.notification

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.notification.NotificationList
import com.devndev.homen.core.domain.repository.NotificationRepository

class GetNotificationsUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(category: String? = null): ApiResult<NotificationList> {
        return notificationRepository.getNotifications(category)
    }
}
