package com.devndev.homen.core.domain.usecase.user

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.user.UpdateProfile
import com.devndev.homen.core.domain.model.user.User
import com.devndev.homen.core.domain.repository.UserRepository

class UpdateProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(updateProfile: UpdateProfile): ApiResult<User> {
        return userRepository.updateProfile(updateProfile)
    }
}