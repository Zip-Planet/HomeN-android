package com.devndev.homen.core.domain.usecase.user

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.UserRepository

class RegisterDeviceUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        registrationId: String,
        platform: String,
        kind: String = "token",
        appVersion: String? = null
    ): ApiResult<Unit> {
        return userRepository.registerDevice(registrationId, platform, kind, appVersion)
    }
}
