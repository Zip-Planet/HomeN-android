package com.devndev.homen.core.data.service.user

import com.devndev.homen.core.data.model.user.request.UpdateProfileRequest
import com.devndev.homen.core.data.model.user.response.GetMyInfoResponse
import com.devndev.homen.core.data.model.user.response.UpdateProfileResponse
import com.devndev.homen.core.data.model.user.response.ValidateNicknameResponse

interface UserService {
    companion object {
        const val GET_MY_INFO = "/users/me/"
        const val UPDATE_PROFILE = "/users/me/"
        const val VALIDATE_NICKNAME = "/users/nicknames/"
    }

    suspend fun getMyInfo(): GetMyInfoResponse

    suspend fun updateProfile(
        request: UpdateProfileRequest
    ): UpdateProfileResponse

    suspend fun validateNickname(nickname: String): ValidateNicknameResponse
}