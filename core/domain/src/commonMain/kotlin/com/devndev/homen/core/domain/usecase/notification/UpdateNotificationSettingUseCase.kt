package com.devndev.homen.core.domain.usecase.notification

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.notification.NotificationSetting
import com.devndev.homen.core.domain.repository.NotificationRepository

class UpdateNotificationSettingUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        pushEnabled: Boolean? = null,
        homeMember: Boolean? = null,
        assignment: Boolean? = null,
        board: Boolean? = null,
        reward: Boolean? = null,
        report: Boolean? = null
    ): ApiResult<NotificationSetting> {
        return notificationRepository.updateNotificationSetting(
            pushEnabled = pushEnabled,
            homeMember = homeMember,
            assignment = assignment,
            board = board,
            reward = reward,
            report = report
        )
    }
}
