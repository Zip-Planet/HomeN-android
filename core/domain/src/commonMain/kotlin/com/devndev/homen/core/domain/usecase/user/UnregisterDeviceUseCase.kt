package com.devndev.homen.core.domain.usecase.user

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.UserRepository

class UnregisterDeviceUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(registrationId: String): ApiResult<Unit> {
        return userRepository.unregisterDevice(registrationId)
    }
}
