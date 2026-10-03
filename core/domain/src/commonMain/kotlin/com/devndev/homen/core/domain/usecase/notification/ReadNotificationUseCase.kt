package com.devndev.homen.core.domain.usecase.notification

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.NotificationRepository

class ReadNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(notificationId: Int): ApiResult<Unit> {
        return notificationRepository.readNotification(notificationId)
    }
}
