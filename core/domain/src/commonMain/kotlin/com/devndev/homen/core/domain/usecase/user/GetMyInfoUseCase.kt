package com.devndev.homen.core.domain.usecase.user

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.user.MyInfo
import com.devndev.homen.core.domain.repository.UserRepository

class GetMyInfoUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ApiResult<MyInfo> {
        return userRepository.getMyInfo()
    }
}