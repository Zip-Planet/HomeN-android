package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.user.request.RegisterDeviceRequest
import com.devndev.homen.core.data.model.user.request.UnregisterDeviceRequest
import com.devndev.homen.core.data.model.user.request.UpdateProfileRequest
import com.devndev.homen.core.data.model.user.response.toDomainModel
import com.devndev.homen.core.data.service.user.UserService
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.user.MyInfo
import com.devndev.homen.core.domain.model.user.UpdateProfile
import com.devndev.homen.core.domain.model.user.User
import com.devndev.homen.core.domain.repository.UserRepository
import io.ktor.client.plugins.ResponseException

class UserRepositoryImpl(
    private val userService: UserService
): UserRepository {
    override suspend fun getMyInfo(): ApiResult<MyInfo> {
        return try {
            val response = userService.getMyInfo()
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun updateProfile(updateProfile: UpdateProfile): ApiResult<User> {
        return try {
            val response = userService.updateProfile(
                UpdateProfileRequest(
                    name = updateProfile.name,
                    profileImage = updateProfile.profileImage
                )
            )
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun validateNickname(nickname: String): ApiResult<Boolean> {
        return try {
            val response = userService.validateNickname(nickname)
            ApiResult.Success(response.isAvailable)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun registerDevice(
        registrationId: String,
        platform: String,
        kind: String,
        appVersion: String?
    ): ApiResult<Unit> {
        return try {
            userService.registerDevice(RegisterDeviceRequest(registrationId, platform, kind, appVersion))
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun unregisterDevice(registrationId: String): ApiResult<Unit> {
        return try {
            userService.unregisterDevice(UnregisterDeviceRequest(registrationId))
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}