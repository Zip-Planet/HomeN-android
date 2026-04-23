package com.devndev.homen.core.data.service.user

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.user.request.UpdateProfileRequest
import com.devndev.homen.core.data.model.user.response.UpdateProfileResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

class UserServiceImpl(
    private val client: HttpClient
): UserService {
    override suspend fun updateProfile(request: UpdateProfileRequest): UpdateProfileResponse {
        return client.patch {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += UserService.UPDATE_PROFILE
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}