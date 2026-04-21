package com.devndev.homen.core.data.service.user

import com.devndev.homen.core.data.model.user.request.UpdateProfileRequest
import com.devndev.homen.core.data.model.user.response.UpdateProfileResponse

interface UserService {
    companion object {
        const val UPDATE_PROFILE = "/users/me/"
    }

    suspend  fun updateProfile(
        request: UpdateProfileRequest
    ): UpdateProfileResponse
}