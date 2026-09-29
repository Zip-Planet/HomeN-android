package com.devndev.homen.core.data.service.notification

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.notification.request.UpdateNotificationSettingRequest
import com.devndev.homen.core.data.model.notification.response.NotificationSettingResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.first

class NotificationServiceImpl(
    private val client: HttpClient,
    private val tokenRepository: TokenRepository
) : NotificationService {

    override suspend fun getNotificationSetting(): NotificationSettingResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += NotificationService.NOTIFICATION_SETTINGS
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun updateNotificationSetting(request: UpdateNotificationSettingRequest): NotificationSettingResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.patch {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += NotificationService.NOTIFICATION_SETTINGS
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(request)
        }.body()
    }
}
