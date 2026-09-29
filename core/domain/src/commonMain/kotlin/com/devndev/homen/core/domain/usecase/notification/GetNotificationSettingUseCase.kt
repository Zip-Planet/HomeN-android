package com.devndev.homen.core.domain.usecase.notification

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.notification.NotificationSetting
import com.devndev.homen.core.domain.repository.NotificationRepository

class GetNotificationSettingUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): ApiResult<NotificationSetting> {
        return notificationRepository.getNotificationSetting()
    }
}
