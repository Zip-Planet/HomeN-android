package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.user.UpdateProfile
import com.devndev.homen.core.domain.model.user.User

interface UserRepository {
    suspend fun updateProfile(updateProfile: UpdateProfile): ApiResult<User>
}