package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.notification.request.UpdateNotificationSettingRequest
import com.devndev.homen.core.data.model.notification.response.toDomainModel
import com.devndev.homen.core.data.service.notification.NotificationService
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.notification.NotificationSetting
import com.devndev.homen.core.domain.repository.NotificationRepository
import io.ktor.client.plugins.ResponseException

class NotificationRepositoryImpl(
    private val notificationService: NotificationService
) : NotificationRepository {

    override suspend fun getNotificationSetting(): ApiResult<NotificationSetting> {
        return try {
            val response = notificationService.getNotificationSetting()
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun updateNotificationSetting(
        pushEnabled: Boolean?,
        homeMember: Boolean?,
        assignment: Boolean?,
        board: Boolean?,
        reward: Boolean?,
        report: Boolean?
    ): ApiResult<NotificationSetting> {
        return try {
            val response = notificationService.updateNotificationSetting(
                UpdateNotificationSettingRequest(
                    pushEnabled = pushEnabled,
                    homeMember = homeMember,
                    assignment = assignment,
                    board = board,
                    reward = reward,
                    report = report
                )
            )
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}
