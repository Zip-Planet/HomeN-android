package com.devndev.homen.core.domain.usecase.user

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.UserRepository

class ValidateNicknameUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): ApiResult<Boolean> {
        return userRepository.validateNickname(nickname)
    }
}