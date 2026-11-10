package com.devndev.homen.core.data.service.user

import com.devndev.homen.core.data.model.user.request.RegisterDeviceRequest
import com.devndev.homen.core.data.model.user.request.UnregisterDeviceRequest
import com.devndev.homen.core.data.model.user.request.UpdateProfileRequest
import com.devndev.homen.core.data.model.user.response.GetMyInfoResponse
import com.devndev.homen.core.data.model.user.response.UpdateProfileResponse
import com.devndev.homen.core.data.model.user.response.ValidateNicknameResponse

interface UserService {
    companion object {
        const val GET_MY_INFO = "/users/me/"
        const val UPDATE_PROFILE = "/users/me/"
        const val VALIDATE_NICKNAME = "/users/nicknames/"
        const val REGISTER_DEVICE = "/users/me/devices/"
        const val UNREGISTER_DEVICE = "/users/me/devices/unregister/"
    }

    suspend fun getMyInfo(): GetMyInfoResponse

    suspend fun updateProfile(
        request: UpdateProfileRequest
    ): UpdateProfileResponse

    suspend fun validateNickname(nickname: String): ValidateNicknameResponse

    suspend fun registerDevice(request: RegisterDeviceRequest)

    suspend fun unregisterDevice(request: UnregisterDeviceRequest)
}